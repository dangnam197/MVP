package com.example.mvp.data.db

import androidx.room.*
import com.example.mvp.data.db.model.Book
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface BookDao {
    @Query("SELECT * FROM Book")
    fun getAll():Flowable<List<Book>>
    @Query("SELECT * FROM Book WHERE id = :bookId")
    fun findById(bookId : Int) :Flowable<Book>
    @Query("SELECT * FROM book WHERE name LIKE :bookName LIMIT 1")
    fun findByName(bookName:String): Flowable<Book>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book) : Completable
    @Delete
    fun delete(book: Book) : Completable
    @Update
    fun update(book: Book) : Completable
}