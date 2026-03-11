package com.example.pokeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {

    fun getUsers(): LiveData<List<User>> {
        val usersLiveData = MutableLiveData<List<User>>()

        apiService.getUsers().enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful && response.body() != null) {
                    val users = response.body()!!
                    // En un escenario real, esto debería hacerse en un hilo de fondo
                    // Pero para seguir el ejemplo de la imagen:
                    // userDao.insertUsers(users) 
                    // Nota: insertUsers es suspend, así que necesitamos un scope.
                    // Para simplificar según el ejercicio, podríamos emitir al LiveData.
                    usersLiveData.value = users
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Manejar error
            }
        })

        return usersLiveData
    }
    
    // Versión alternativa sugerida por la primera imagen que usa corrutinas
    suspend fun getUsersSync(): List<User>? {
        return try {
            val response = apiService.getUsers().execute()
            if (response.isSuccessful) {
                response.body()?.also {
                    userDao.insertUsers(it)
                }
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}
