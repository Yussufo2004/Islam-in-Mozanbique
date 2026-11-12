package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar

class IslamicRepository(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences("noor_islam_prefs", Context.MODE_PRIVATE)

    private val _selectedCity = MutableStateFlow(
        MOZAMBIQUE_CITIES.find { it.name == prefs.getString("selected_city", "Maputo") } ?: MOZAMBIQUE_CITIES.first()
    )
    val selectedCity: StateFlow<CityLocation> = _selectedCity.asStateFlow()

    private val _calculationMethod = MutableStateFlow(
        CalculationMethod.values().find { it.name == prefs.getString("calc_method", CalculationMethod.MOZAMBIQUE_STANDARD.name) }
            ?: CalculationMethod.MOZAMBIQUE_STANDARD
    )
    val calculationMethod: StateFlow<CalculationMethod> = _calculationMethod.asStateFlow()

    // Tasbih / Dhikr
    private val _dhikrCount = MutableStateFlow(prefs.getInt("dhikr_count", 0))
    val dhikrCount: StateFlow<Int> = _dhikrCount.asStateFlow()

    private val _dhikrTarget = MutableStateFlow(prefs.getInt("dhikr_target", 33))
    val dhikrTarget: StateFlow<Int> = _dhikrTarget.asStateFlow()

    private val _currentDhikrPhrase = MutableStateFlow(
        prefs.getString("dhikr_phrase", "سُبْحَانَ اللَّهِ (SubhanAllah)") ?: "سُبْحَانَ اللَّهِ (SubhanAllah)"
    )
    val currentDhikrPhrase: StateFlow<String> = _currentDhikrPhrase.asStateFlow()

    // User Profile
    private val _userProfile = MutableStateFlow(
        UserProfile(
            name = prefs.getString("user_name", "Irmão Paulo") ?: "Irmão Paulo",
            username = prefs.getString("user_username", "@paulo_moz") ?: "@paulo_moz",
            email = "paulo@exemplo.mz",
            bio = prefs.getString("user_bio", "Procurando aprender mais sobre o Islam e partilhar fraternidade em Moçambique.")
                ?: "Procurando aprender mais sobre o Islam e partilhar fraternidade em Moçambique.",
            country = "Moçambique",
            city = prefs.getString("user_city", "Maputo") ?: "Maputo",
            joinedDate = "Novembro de 2026",
            totalDhikr = prefs.getInt("total_dhikr_alltime", 240),
            completedQuizzes = prefs.getInt("quizzes_completed", 3),
            savedPostsCount = 2
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // Community Posts
    private val initialPosts = mutableListOf(
        CommunityPost(
            id = "p0",
            authorName = "Histórias e Lições Islâmicas",
            authorCity = "Moçambique",
            authorRole = "Canal Oficial",
            groupId = "channel_stories",
            groupName = "Histórias e Lições Islâmicas",
            title = "A Paciência de Ayyub (Job) e a Confiança em Allah",
            content = "Assalamu Alaikum irmãos e irmãs! No novo episódio do nosso canal, partilhamos como o Nobre Profeta Ayyub enfrentou a perda com gratidão inabalável. Quando colocamos a nossa confiança sincera (Tawakkul) em Deus, nenhuma dor é em vão. Confiram também na secção 'Aprender' todas as lições dos Profetas!",
            timestampFormatted = "Hoje, às 08:00",
            likesCount = 128,
            isLiked = true,
            isSaved = true,
            comments = listOf(
                CommentItem("c0", "p0", "Mussa Chale", "Nampula", "MashaAllah! Este canal traz reflexões profundas para a nossa juventude em Moçambique.", "Hoje, 08:35")
            )
        ),
        CommunityPost(
            id = "p1",
            authorName = "Sheikh Bilal Mussa",
            authorCity = "Maputo",
            authorRole = "Imam Local",
            groupId = "mozambique",
            groupName = "Comunidade de Moçambique",
            title = "Acolhimento aos jovens na Mesquita da Baixa",
            content = "Assalamu Alaikum irmãos e irmãs de Maputo. Convidamos todos para as aulas semanais de Tajweed e diálogo fraterno todos os sábados após a oração de Asr. Aberto também para amigos não-muçulmanos que queiram conhecer a nossa fé.",
            timestampFormatted = "Hoje, às 10:30",
            likesCount = 38,
            isLiked = false,
            isSaved = true,
            comments = listOf(
                CommentItem("c1", "p1", "Aminah Sulemane", "Matola", "MashaAllah, que iniciativa nobre! Estarei presente com a minha família.", "Hoje, 11:00"),
                CommentItem("c2", "p1", "Zubair Cassamo", "Maputo", "Alhamdulillah, o diálogo e a união são essenciais.", "Hoje, 11:24")
            )
        ),
        CommunityPost(
            id = "p2",
            authorName = "Farida Bachir",
            authorCity = "Nampula",
            authorRole = "Estudante",
            groupId = "quran_students",
            groupName = "Estudantes do Qur'an",
            title = "Dica para memorizar a Surah Al-Mulk",
            content = "Irmãos, para memorizar 2 versículos por dia com consistência: escutem o recitador Al-Husary pela manhã e repitam na Sunnah antes de dormir. Quem aqui está a memorizar Al-Mulk?",
            timestampFormatted = "Ontem",
            likesCount = 54,
            isLiked = true,
            isSaved = false,
            comments = listOf(
                CommentItem("c3", "p2", "Mariamo Aly", "Beira", "Eu comecei esta semana! Shukran pela dica valiosa.", "Ontem")
            )
        ),
        CommunityPost(
            id = "p3",
            authorName = "Carlos (Interessado)",
            authorCity = "Beira",
            authorRole = "Visitante",
            groupId = "discover_islam",
            groupName = "Conhecer o Islam",
            title = "Dúvida respeitosa sobre o significado do Tawhid",
            content = "Olá a todos! Não sou muçulmano mas tenho um imenso respeito pela vossa comunidade e gostava de compreender melhor o conceito de Tawhid e a visão islâmica sobre a paz e o perdão. Podem indicar-me leituras?",
            timestampFormatted = "Há 2 dias",
            likesCount = 67,
            isLiked = true,
            isSaved = true,
            comments = listOf(
                CommentItem("c4", "p3", "Umar Daudo", "Maputo", "Seja muito bem-vindo Carlos! O Tawhid é a crença pura de que Deus é Único. Na secção 'Aprender' desta aplicação encontras a explicação de Aqidah com versículos autênticos.", "Há 2 dias")
            )
        ),
        CommunityPost(
            id = "p4",
            authorName = "Yassine Camal",
            authorCity = "Quelimane",
            authorRole = "Membro",
            groupId = "learn_salah",
            groupName = "Aprender Salah",
            title = "A importância da calma e concentração (Khushu') na oração",
            content = "Não tenham pressa ao rezar. O Profeta ﷺ ensinou que a oração deve ser feita com serenidade em cada postura: no Ruku', no Sujud e no sentar.",
            timestampFormatted = "Há 3 dias",
            likesCount = 42,
            isLiked = false,
            isSaved = false
        )
    )

    private val _posts = MutableStateFlow<List<CommunityPost>>(initialPosts)
    val posts: StateFlow<List<CommunityPost>> = _posts.asStateFlow()

    fun selectCity(city: CityLocation) {
        _selectedCity.value = city
        prefs.edit().putString("selected_city", city.name).apply()
    }

    fun selectCalculationMethod(method: CalculationMethod) {
        _calculationMethod.value = method
        prefs.edit().putString("calc_method", method.name).apply()
    }

    fun incrementDhikr(): Boolean {
        val newCount = _dhikrCount.value + 1
        _dhikrCount.value = newCount
        prefs.edit().putInt("dhikr_count", newCount).apply()

        val allTime = _userProfile.value.totalDhikr + 1
        _userProfile.value = _userProfile.value.copy(totalDhikr = allTime)
        prefs.edit().putInt("total_dhikr_alltime", allTime).apply()

        // Returns true if target completed
        return newCount % _dhikrTarget.value == 0
    }

    fun resetDhikr() {
        _dhikrCount.value = 0
        prefs.edit().putInt("dhikr_count", 0).apply()
    }

    fun setDhikrTarget(target: Int) {
        _dhikrTarget.value = target
        prefs.edit().putInt("dhikr_target", target).apply()
    }

    fun setDhikrPhrase(phrase: String) {
        _currentDhikrPhrase.value = phrase
        prefs.edit().putString("dhikr_phrase", phrase).apply()
    }

    fun toggleLikePost(postId: String) {
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                val newLiked = !post.isLiked
                val newCount = if (newLiked) post.likesCount + 1 else post.likesCount - 1
                post.copy(isLiked = newLiked, likesCount = newCount)
            } else post
        }
    }

    fun toggleSavePost(postId: String) {
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(isSaved = !post.isSaved)
            } else post
        }
    }

    fun addComment(postId: String, text: String, authorName: String, authorCity: String) {
        val newComment = CommentItem(
            id = "c_${System.currentTimeMillis()}",
            postId = postId,
            authorName = authorName,
            authorCity = authorCity,
            text = text,
            timestampFormatted = "Agora mesmo"
        )
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(comments = post.comments + newComment)
            } else post
        }
    }

    fun createPost(title: String, content: String, group: CommunityGroup) {
        val newPost = CommunityPost(
            id = "p_${System.currentTimeMillis()}",
            authorName = _userProfile.value.name,
            authorCity = _userProfile.value.city,
            authorRole = "Membro",
            groupId = group.id,
            groupName = group.name,
            title = title,
            content = content,
            timestampFormatted = "Agora mesmo",
            likesCount = 0,
            isLiked = false,
            isSaved = false,
            comments = emptyList()
        )
        _posts.value = listOf(newPost) + _posts.value
    }

    fun reportPost(postId: String, reason: String) {
        _posts.value = _posts.value.map { post ->
            if (post.id == postId) {
                post.copy(isReported = true)
            } else post
        }
    }

    fun updateUserProfile(name: String, bio: String, city: String) {
        val updated = _userProfile.value.copy(name = name, bio = bio, city = city)
        _userProfile.value = updated
        prefs.edit()
            .putString("user_name", name)
            .putString("user_bio", bio)
            .putString("user_city", city)
            .apply()
    }

    fun recordQuizCompleted() {
        val count = _userProfile.value.completedQuizzes + 1
        _userProfile.value = _userProfile.value.copy(completedQuizzes = count)
        prefs.edit().putInt("quizzes_completed", count).apply()
    }
}
