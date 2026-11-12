package com.example.data.model

data class CommunityGroup(
    val id: String,
    val name: String,
    val description: String,
    val memberCount: Int,
    val iconEmoji: String
)

val INITIAL_GROUPS = listOf(
    CommunityGroup(
        id = "channel_stories",
        name = "Histórias e Lições Islâmicas",
        description = "Canal oficial: narrativas dos Profetas, nobres Companheiros (Sahaba) e lições de vida para fortalecer a fé em Moçambique e no mundo.",
        memberCount = 3820,
        iconEmoji = "🎙️"
    ),
    CommunityGroup(
        id = "mozambique",
        name = "Comunidade de Moçambique",
        description = "Espaço para os muçulmanos de Moçambique (Maputo, Beira, Nampula e todas as províncias). Partilha de horários locais, mesquitas, eventos de solidariedade e notícias.",
        memberCount = 1240,
        iconEmoji = "🇲🇿"
    ),
    CommunityGroup(
        id = "quran_students",
        name = "Estudantes do Qur'an",
        description = "Memorização (Hifz), regras de Tajweed, reflexões diárias (Tadabbur) e dicas de estudo do Livro Sagrado.",
        memberCount = 890,
        iconEmoji = "📖"
    ),
    CommunityGroup(
        id = "learn_arabic",
        name = "Aprender Árabe",
        description = "Vocabulário do quotidiano e litúrgico, gramática árabe básica e pronúncia de letras.",
        memberCount = 745,
        iconEmoji = "✍️"
    ),
    CommunityGroup(
        id = "learn_salah",
        name = "Aprender Salah",
        description = "Apoio prático para aperfeiçoar a oração diária, tirar dúvidas sobre Wudu e posturas da oração.",
        memberCount = 612,
        iconEmoji = "🕌"
    ),
    CommunityGroup(
        id = "discover_islam",
        name = "Conhecer o Islam",
        description = "Espaço aberto e respeitoso para visitantes e interessados conhecerem os princípios, a ética e a mensagem de paz do Islam.",
        memberCount = 530,
        iconEmoji = "🤝"
    )
)

data class CommentItem(
    val id: String,
    val postId: String,
    val authorName: String,
    val authorCity: String,
    val text: String,
    val timestampFormatted: String
)

data class CommunityPost(
    val id: String,
    val authorName: String,
    val authorCity: String,
    val authorRole: String = "Membro",
    val groupId: String,
    val groupName: String,
    val title: String,
    val content: String,
    val timestampFormatted: String,
    val likesCount: Int = 0,
    val isLiked: Boolean = false,
    val isSaved: Boolean = false,
    val comments: List<CommentItem> = emptyList(),
    val isReported: Boolean = false
)

data class UserProfile(
    val name: String,
    val username: String,
    val email: String,
    val bio: String,
    val country: String,
    val city: String,
    val joinedDate: String,
    val totalDhikr: Int = 132,
    val completedQuizzes: Int = 4,
    val savedPostsCount: Int = 3
)
