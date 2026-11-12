package com.example.viewmodel

import android.app.Application
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.*
import com.example.data.repository.IslamicRepository
import com.example.data.repository.PrayerCalculator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Calendar

enum class MainTab(val title: String, val iconName: String) {
    HOME("Início", "home"),
    LEARN("Aprender", "book"),
    COMMUNITY("Comunidade", "people"),
    DAILY("Prática diária", "schedule"),
    PROFILE("Perfil", "person")
}

enum class LearnSection(val title: String) {
    OVERVIEW("Visão Geral"),
    QURAN("Nobre Qur'an"),
    SALAH("Como Realizar Salah"),
    TAHARAH("Taharah (Purificação)"),
    AQIDAH("Aqidah (Crença)"),
    DUAS("Duas do Quotidiano"),
    DHIKR("Dhikr & Benefícios"),
    PROPHETS("História dos Profetas"),
    AKHLAQ("Bom Comportamento"),
    QUIZ("Quiz Islâmico")
}

enum class DailyTool(val title: String) {
    PRAYERS("Horários de Salah"),
    QIBLA("Bússola da Qibla"),
    TASBIH("Contador de Dhikr"),
    CALENDAR("Calendário Hijri")
}

class IslamicViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = IslamicRepository(application.applicationContext)

    // Current Main Tab
    private val _currentTab = MutableStateFlow(MainTab.HOME)
    val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

    // Learn Screen Sub-navigation
    private val _learnSection = MutableStateFlow(LearnSection.OVERVIEW)
    val learnSection: StateFlow<LearnSection> = _learnSection.asStateFlow()

    private val _selectedSurah = MutableStateFlow<Surah?>(null)
    val selectedSurah: StateFlow<Surah?> = _selectedSurah.asStateFlow()

    // Daily Tool Sub-navigation
    private val _dailyTool = MutableStateFlow(DailyTool.PRAYERS)
    val dailyTool: StateFlow<DailyTool> = _dailyTool.asStateFlow()

    // Repository flows
    val selectedCity = repository.selectedCity
    val calculationMethod = repository.calculationMethod
    val dhikrCount = repository.dhikrCount
    val dhikrTarget = repository.dhikrTarget
    val currentDhikrPhrase = repository.currentDhikrPhrase
    val userProfile = repository.userProfile
    val posts = repository.posts

    // Calculated Prayer Times & Qibla
    private val _prayerTimes = MutableStateFlow<List<PrayerTime>>(emptyList())
    val prayerTimes: StateFlow<List<PrayerTime>> = _prayerTimes.asStateFlow()

    private val _qiblaBearing = MutableStateFlow(14.0f)
    val qiblaBearing: StateFlow<Float> = _qiblaBearing.asStateFlow()

    private val _hijriDateString = MutableStateFlow("")
    val hijriDateString: StateFlow<String> = _hijriDateString.asStateFlow()

    private val _gregorianDateString = MutableStateFlow(PrayerCalculator.getRealGregorianDateFormatted())
    val gregorianDateString: StateFlow<String> = _gregorianDateString.asStateFlow()

    // Community filter & modals
    private val _selectedGroupId = MutableStateFlow<String?>(null)
    val selectedGroupId: StateFlow<String?> = _selectedGroupId.asStateFlow()

    private val _activeCommentPost = MutableStateFlow<CommunityPost?>(null)
    val activeCommentPost: StateFlow<CommunityPost?> = _activeCommentPost.asStateFlow()

    private val _showCreatePostDialog = MutableStateFlow(false)
    val showCreatePostDialog: StateFlow<Boolean> = _showCreatePostDialog.asStateFlow()

    private val _reportingPostId = MutableStateFlow<String?>(null)
    val reportingPostId: StateFlow<String?> = _reportingPostId.asStateFlow()

    private val _showCommunityRules = MutableStateFlow(false)
    val showCommunityRules: StateFlow<Boolean> = _showCommunityRules.asStateFlow()

    // Quiz State
    private val _quizCurrentIndex = MutableStateFlow(0)
    val quizCurrentIndex: StateFlow<Int> = _quizCurrentIndex.asStateFlow()

    private val _quizSelectedOption = MutableStateFlow<Int?>(null)
    val quizSelectedOption: StateFlow<Int?> = _quizSelectedOption.asStateFlow()

    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()

    private val _quizFinished = MutableStateFlow(false)
    val quizFinished: StateFlow<Boolean> = _quizFinished.asStateFlow()

    // Notification message for feedback (Snackbar / Toast)
    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    init {
        refreshPrayerCalculations()
        // Periodically refresh prayer times so "isNext" stays accurate
        viewModelScope.launch {
            while (true) {
                delay(60000)
                refreshPrayerCalculations()
            }
        }
    }

    fun setMainTab(tab: MainTab) {
        _currentTab.value = tab
    }

    fun setLearnSection(section: LearnSection) {
        _learnSection.value = section
        if (section != LearnSection.QURAN) {
            _selectedSurah.value = null
        }
    }

    fun selectSurah(surah: Surah?) {
        _selectedSurah.value = surah
    }

    fun setDailyTool(tool: DailyTool) {
        _dailyTool.value = tool
    }

    fun selectCity(city: CityLocation) {
        repository.selectCity(city)
        refreshPrayerCalculations()
        showMessage("Localização definida para ${city.name}, ${city.country}")
    }

    fun selectCalculationMethod(method: CalculationMethod) {
        repository.selectCalculationMethod(method)
        refreshPrayerCalculations()
        showMessage("Método de cálculo atualizado: ${method.displayName}")
    }

    fun refreshPrayerCalculations() {
        val city = selectedCity.value
        val method = calculationMethod.value
        val cal = Calendar.getInstance()
        _prayerTimes.value = PrayerCalculator.calculatePrayerTimes(city, cal, method)
        _qiblaBearing.value = PrayerCalculator.calculateQiblaBearing(city.latitude, city.longitude)
        _hijriDateString.value = PrayerCalculator.getHijriDate(cal)
        _gregorianDateString.value = PrayerCalculator.getRealGregorianDateFormatted(cal)
    }

    fun onTasbihTap() {
        val targetReached = repository.incrementDhikr()
        vibratePhone(if (targetReached) 150L else 35L)
        if (targetReached) {
            showMessage("MashaAllah! Meta de ${dhikrTarget.value} dhikrs completada!")
        }
    }

    fun resetTasbih() {
        repository.resetDhikr()
        showMessage("Contador de Dhikr reiniciado.")
    }

    fun setDhikrTarget(target: Int) {
        repository.setDhikrTarget(target)
    }

    fun setDhikrPhrase(phrase: String) {
        repository.setDhikrPhrase(phrase)
    }

    fun filterByGroup(groupId: String?) {
        _selectedGroupId.value = groupId
    }

    fun toggleLikePost(postId: String) {
        repository.toggleLikePost(postId)
    }

    fun toggleSavePost(postId: String) {
        repository.toggleSavePost(postId)
        val post = posts.value.find { it.id == postId }
        if (post != null) {
            val wasSaved = post.isSaved
            showMessage(if (!wasSaved) "Publicação guardada nos teus favoritos!" else "Publicação removida dos favoritos.")
        }
    }

    fun openCommentsFor(post: CommunityPost?) {
        _activeCommentPost.value = post
    }

    fun addCommentToActive(commentText: String) {
        val post = _activeCommentPost.value ?: return
        if (commentText.trim().isBlank()) return
        val user = userProfile.value
        repository.addComment(post.id, commentText.trim(), user.name, user.city)
        // Refresh active comment post
        _activeCommentPost.value = posts.value.find { it.id == post.id }
        showMessage("Comentário adicionado com sucesso!")
    }

    fun openCreatePost(open: Boolean) {
        _showCreatePostDialog.value = open
    }

    fun createPost(title: String, content: String, group: CommunityGroup) {
        if (title.isBlank() || content.isBlank()) {
            showMessage("Por favor, preencha o título e o conteúdo.")
            return
        }
        repository.createPost(title, content, group)
        _showCreatePostDialog.value = false
        showMessage("Publicação partilhada com a ${group.name}!")
    }

    fun openReportDialog(postId: String?) {
        _reportingPostId.value = postId
    }

    fun submitReport(reason: String) {
        val id = _reportingPostId.value ?: return
        repository.reportPost(id, reason)
        _reportingPostId.value = null
        showMessage("Obrigado pela denúncia. A nossa moderação irá avaliar o conteúdo para garantir o respeito e a autenticidade.")
    }

    fun openCommunityRules(open: Boolean) {
        _showCommunityRules.value = open
    }

    fun updateUserProfile(name: String, bio: String, city: String) {
        repository.updateUserProfile(name, bio, city)
        showMessage("Perfil atualizado com sucesso!")
    }

    // Quiz Functions
    fun answerQuizQuestion(optionIndex: Int) {
        if (_quizSelectedOption.value != null) return // already answered
        _quizSelectedOption.value = optionIndex
        val currentQ = ISLAMIC_QUIZ_QUESTIONS.getOrNull(_quizCurrentIndex.value) ?: return
        if (optionIndex == currentQ.correctIndex) {
            _quizScore.value += 1
            vibratePhone(40L)
        } else {
            vibratePhone(100L)
        }
    }

    fun nextQuizQuestion() {
        val nextIdx = _quizCurrentIndex.value + 1
        if (nextIdx < ISLAMIC_QUIZ_QUESTIONS.size) {
            _quizCurrentIndex.value = nextIdx
            _quizSelectedOption.value = null
        } else {
            _quizFinished.value = true
            repository.recordQuizCompleted()
        }
    }

    fun restartQuiz() {
        _quizCurrentIndex.value = 0
        _quizSelectedOption.value = null
        _quizScore.value = 0
        _quizFinished.value = false
    }

    fun showMessage(msg: String) {
        _userMessage.value = msg
    }

    fun clearMessage() {
        _userMessage.value = null
    }

    private fun vibratePhone(durationMs: Long) {
        try {
            val context = getApplication<Application>().applicationContext
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                vibrator?.vibrate(durationMs)
            }
        } catch (_: Exception) {
            // gracefully ignored if hardware vibrator unavailable
        }
    }
}
