package unj.cs.student.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {

    @Query("SELECT * FROM students ORDER BY _id ASC")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students where _id = :_id")
    fun getStudentById(_id: Int): Flow<Student>

    @Query("SELECT * FROM students where name = :name")
    fun getStudentByName(name: String): Flow<Student>

    @Query("SELECT * FROM students where uid = :uid or name = :name")
    fun getStudentOr(uid: String, name: String): Flow<Student>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg student: Student)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun update(student: Student)

    @Delete
    suspend fun delete(student: Student)

}