//package ru.fesenko.helloweather
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import androidx.room.Database
//import androidx.room.RoomDatabase
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import androidx.lifecycle.viewModelScope
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//@Entity
//data class Settings(
//    @PrimaryKey(autoGenerate = true) val id: Int = 0,
//    val brightness: Int // Яркость экрана
//)
//
//
//@Dao
//interface SettingsDao {
//    @Insert
//    suspend fun insert(settings: Settings)
//
//    @Query("SELECT * FROM settings ORDER BY id DESC LIMIT 1")
//    suspend fun getSettings(): Settings?
//}
//
//
//
//@Database(entities = [Settings::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun settingsDao(): SettingsDao
//}
//
//
//
//class SettingsViewModel(private val settingsDao: SettingsDao) : ViewModel() {
//    private val _settings = MutableStateFlow<Settings?>(null)
//    val settings = _settings.asStateFlow()
//
//    init {
//        viewModelScope.launch {
//            _settings.value = settingsDao.getSettings()
//        }
//    }
//
//    fun saveSettings(brightness: Int) {
//        viewModelScope.launch {
//            settingsDao.insert(Settings(brightness = brightness))
//            _settings.value = settingsDao.getSettings()
//        }
//    }
//}
//class ViewModelFactory(private val settingsDao: SettingsDao) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return SettingsViewModel(settingsDao) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
