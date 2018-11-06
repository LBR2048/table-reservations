package com.example.leonardo.waiterapp.data.reservations

import androidx.room.*

@Dao
interface ReservationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reservation: Reservation)

    // TODO completar
    @Delete
    fun delete(reservation: Reservation)

    @Query("DELETE FROM reservations")
    fun deleteAll()
}