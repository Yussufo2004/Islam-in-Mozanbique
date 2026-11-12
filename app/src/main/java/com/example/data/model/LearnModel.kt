package com.example.data.model

data class SalahStep(
    val stepNumber: Int,
    val title: String,
    val arabicRecitation: String,
    val transliteration: String,
    val translation: String,
    val description: String,
    val isFard: Boolean = true
)

data class TaharahTopic(
    val title: String,
    val description: String,
    val steps: List<String>,
    val nullifiers: List<String> = emptyList(),
    val reference: String
)

data class AqidahPillar(
    val number: Int,
    val name: String,
    val arabicName: String,
    val explanation: String,
    val quranicReference: String
)

data class DailyDuaItem(
    val id: String,
    val title: String,
    val situation: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val reference: String,
    val category: String
)

data class ProphetStory(
    val name: String,
    val arabicName: String,
    val era: String,
    val summary: String,
    val keyLessons: List<String>,
    val reference: String
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val authenticReference: String
)

val SALAH_GUIDE_STEPS = listOf(
    SalahStep(
        stepNumber = 1,
        title = "Niyyah & Takbir al-Ihram",
        arabicRecitation = "اللَّهُ أَكْبَرُ",
        transliteration = "Allāhu Akbar",
        translation = "Allah é o Maior.",
        description = "Formular no coração a intenção sincera de rezar a oração específica (Fajr, Dhuhr, etc.) para Allah. Levantar as mãos até à altura dos ombros ou lóbulos das orelhas e pronunciar o Takbir de abertura.",
        isFard = true
    ),
    SalahStep(
        stepNumber = 2,
        title = "Qiyam (De pé) & Al-Fatihah",
        arabicRecitation = "أَعُوذُ بِاللَّهِ مِنَ الشَّيْطَانِ الرَّجِيمِ\nبِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ\nالْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ...",
        transliteration = "A'ūdhu billāhi minash-shayṭānir-rajīm. Bismillāhir-raḥmānir-raḥīm...",
        translation = "Refugio-me em Allah contra Satanás, o apedrejado. Em nome de Allah, o Clemente, o Misericordioso...",
        description = "Colocar a mão direita sobre a esquerda sobre o peito. Recitar Surah Al-Fatihah (obrigatória em cada rak'ah) seguida de uma surah adicional nas duas primeiras rak'ahs.",
        isFard = true
    ),
    SalahStep(
        stepNumber = 3,
        title = "Ruku' (Inclinação)",
        arabicRecitation = "سُبْحَانَ رَبِّيَ الْعَظِيمِ",
        transliteration = "Subḥāna Rabbiyal-'Aẓīm (3x)",
        translation = "Glorificado seja o meu Senhor, o Grandioso.",
        description = "Inclinar as costas retas, apoiando as mãos sobre os joelhos com os dedos abertos. Dizer com tranquilidade e devoção 3 vezes.",
        isFard = true
    ),
    SalahStep(
        stepNumber = 4,
        title = "I'tidal (Retorno à postura ereta)",
        arabicRecitation = "سَمِعَ اللَّهُ لِمَنْ حَمِدَهُ\nرَبَّنَا وَلَكَ الْحَمْدُ",
        transliteration = "Sami'allāhu liman ḥamidah. Rabbanā walakal-ḥamd.",
        translation = "Allah ouve a quem O louva. Ó nosso Senhor, e a Ti pertence todo o louvor.",
        description = "Erguer o corpo completamente até estar reto e tranquilo antes de descer para a prostração.",
        isFard = true
    ),
    SalahStep(
        stepNumber = 5,
        title = "Sujud (Prostração)",
        arabicRecitation = "سُبْحَانَ رَبِّيَ الْأَعْلَى",
        transliteration = "Subḥāna Rabbiyal-A'lā (3x)",
        translation = "Glorificado seja o meu Senhor, o Altíssimo.",
        description = "Prostrar-se no chão sobre 7 partes do corpo: a testa com o nariz, as duas mãos, os dois joelhos e as pontas dos dedos de ambos os pés virados para a Qibla.",
        isFard = true
    ),
    SalahStep(
        stepNumber = 6,
        title = "Jalsa (Sentar entre as prostrações)",
        arabicRecitation = "رَبِّ اغْفِرْ لِي، رَبِّ اغْفِرْ لِي",
        transliteration = "Rabbighfir lī, Rabbighfir lī",
        translation = "Senhor meu, perdoa-me; Senhor meu, perdoa-me.",
        description = "Sentar-se sobre a perna esquerda mantendo o pé direito vertical. Fazer esta pausa com calma antes do segundo Sujud.",
        isFard = true
    ),
    SalahStep(
        stepNumber = 7,
        title = "Tashahhud Final",
        arabicRecitation = "التَّحِيَّاتُ لِلَّهِ وَالصَّلَوَاتُ وَالطَّيِّبَاتُ، السَّلَامُ عَلَيْكَ أَيُّهَا النَّبِيُّ وَرَحْمَةُ اللَّهِ وَبَرَكَاتُهُ، السَّلَامُ عَلَيْنَا وَعَلَى عِبَادِ اللَّهِ الصَّالِحِينَ. أَشْهَدُ أَنْ لَا إِلَٰهَ إِلَّا اللَّهُ وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ.",
        transliteration = "At-taḥiyyātu lillāhi waṣ-ṣalawātu waṭ-ṭayyibāt, as-salāmu 'alayka ayyuhan-nabiyyu wa raḥmatullāhi wa barakātuh, as-salāmu 'alaynā wa 'alā 'ibādillāhiṣ-ṣāliḥīn. Ash-hadu allā ilāha illallāh, wa ash-hadu anna Muḥammadan 'abduhū wa rasūluh.",
        translation = "Todas as saudações, orações e coisas boas pertencem a Allah. A paz esteja contigo, ó Profeta, e a misericórdia de Allah e Suas bênçãos. A paz esteja sobre nós e sobre os servos retos de Allah. Testemunho que não há divindade digna de adoração exceto Allah, e testemunho que Muhammad é Seu servo e Mensageiro.",
        description = "Sentado na última rak'ah, recitar o Tashahhud seguido da oração abraâmica (Salawat Ibrahimiyyah).",
        isFard = true
    ),
    SalahStep(
        stepNumber = 8,
        title = "Taslim (Conclusão)",
        arabicRecitation = "السَّلَامُ عَلَيْكُمْ وَرَحْمَةُ اللَّهِ",
        transliteration = "As-Salāmu 'alaykum wa raḥmatullāh",
        translation = "Que a paz e a misericórdia de Allah estejam convosco.",
        description = "Virar a cabeça para o lado direito e depois para o lado esquerdo, concluindo a oração.",
        isFard = true
    )
)

val AUTHENTIC_DUAS = listOf(
    DailyDuaItem(
        id = "dua_morning",
        title = "Ao acordar pela manhã",
        situation = "Ao despertar do sono",
        arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَحْيَانَا بَعْدَ مَا أَمَاتَنَا وَإِلَيْهِ النُّشُورُ",
        transliteration = "Al-ḥamdu lillāhil-ladhī aḥyānā ba'da mā amātanā wa ilayhin-nushūr.",
        translation = "Louvado seja Allah, que nos devolveu a vida após a nossa morte (sono), e a Ele é o Retorno.",
        reference = "Sahih al-Bukhari nº 6312, Sahih Muslim nº 2711",
        category = "Quotidiano"
    ),
    DailyDuaItem(
        id = "dua_leave_home",
        title = "Ao sair de casa",
        situation = "Ao passar a porta de casa",
        arabic = "بِسْمِ اللَّهِ، تَوَكَّلْتُ عَلَى اللَّهِ، وَلَا حَوْلَ وَلَا قُوَّةَ إِلَّا بِاللَّهِ",
        transliteration = "Bismillāh, tawakkaltu 'alallāh, wa lā ḥawla wa lā quwwata illā billāh.",
        translation = "Em nome de Allah, confio em Allah; e não há poder nem força senão em Allah.",
        reference = "Abu Dawud nº 5095, At-Tirmidhi nº 3426 (Autêntico)",
        category = "Proteção"
    ),
    DailyDuaItem(
        id = "dua_enter_home",
        title = "Ao entrar em casa",
        situation = "Ao regressar ao lar",
        arabic = "بِسْمِ اللَّهِ وَلَجْنَا، وَبِسْمِ اللَّهِ خَرَجْنَا، وَعَلَى رَبِّنَا تَوَكَّلْنَا",
        transliteration = "Bismillāhi walajnā, wa bismillāhi kharajnā, wa 'alā rabbinā tawakkalnā.",
        translation = "Em nome de Allah entramos, e em nome de Allah saímos, e em nosso Senhor confiamos.",
        reference = "Abu Dawud nº 5096",
        category = "Quotidiano"
    ),
    DailyDuaItem(
        id = "dua_before_eating",
        title = "Antes da refeição",
        situation = "Antes de começar a comer",
        arabic = "بِسْمِ اللَّهِ",
        transliteration = "Bismillāh (se esquecer: Bismillāhi fī awwalihi wa ākhirih)",
        translation = "Em nome de Allah (se esquecer no início: 'Em nome de Allah no seu início e no seu fim').",
        reference = "Sahih al-Bukhari nº 5376, At-Tirmidhi nº 1858",
        category = "Refeição"
    ),
    DailyDuaItem(
        id = "dua_after_eating",
        title = "Após a refeição",
        situation = "Ao terminar de comer e beber",
        arabic = "الْحَمْدُ لِلَّهِ الَّذِي أَطْعَمَنِي هَٰذَا وَرَزَقَنِيهِ مِنْ غَيْرِ حَوْلٍ مِنِّي وَلَا قُوَّةٍ",
        transliteration = "Al-ḥamdu lillāhil-ladhī aṭ'amanī hādhā wa razaqanīhi min ghayri ḥawlin minnī wa lā quwwah.",
        translation = "Louvado seja Allah, que me alimentou com isto e me agraciou sem qualquer poder ou força de minha parte.",
        reference = "Abu Dawud nº 4023, At-Tirmidhi nº 3458 (Hasan)",
        category = "Refeição"
    ),
    DailyDuaItem(
        id = "dua_before_sleep",
        title = "Antes de dormir",
        situation = "Deitando-se sobre o lado direito",
        arabic = "بِاسْمِكَ رَبِّي وَضَعْتُ جَنْبِي، وَبِكَ أَرْفَعُهُ، فَإِنْ أَمْسَكْتَ نَفْسِي فَارْحَمْهَا، وَإِنْ أَرْسَلْتَهَا فَاحْفَظْهَا بِمَا تَحْفَظُ بِهِ عِبَادَكَ الصَّالِحِينَ",
        transliteration = "Bismika Rabbī waḍa'tu janbī, wa bika arfa'uh, fa in amsakta nafsī farḥamhā, wa in arsaltahā faḥfaẓhā bimā taḥfaẓu bihī 'ibādakaṣ-ṣāliḥīn.",
        translation = "Em Teu nome, meu Senhor, deito o meu corpo, e por Ti o levanto. Se retiveres a minha alma, tem misericórdia dela; e se a devolveres, protege-a como proteges os Teus servos virtuosos.",
        reference = "Sahih al-Bukhari nº 6320, Sahih Muslim nº 2714",
        category = "Quotidiano"
    ),
    DailyDuaItem(
        id = "dua_distress",
        title = "Em momento de aflição e dificuldade",
        situation = "Quando passar por momentos de ansiedade ou tristeza",
        arabic = "لَا إِلَٰهَ إِلَّا أَنْتَ سُبْحَانَكَ إِنِّي كُنْتُ مِنَ الظَّالِمِينَ",
        transliteration = "Lā ilāha illā Anta, subḥānaka innī kuntu minaẓ-ẓālimīn.",
        translation = "Não há divindade além de Ti! Glorificado sejas! Em verdade, eu estive entre os injustos.",
        reference = "Qur'an 21:87 (Súplica de Yunus/Jonas), At-Tirmidhi nº 3505",
        category = "Paz e Alívio"
    )
)

val HADITH_DO_DIA = DailyDuaItem(
    id = "hadith_1",
    title = "A Sinceridade nas Ações (An-Niyyah)",
    situation = "Princípio fundamental de todas as obras no Islam",
    arabic = "إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى",
    transliteration = "Innamal-a'mālu bin-niyyāt, wa innamā likullimri'in mā nawā.",
    translation = "As ações dependem exclusivamente das intenções, e cada pessoa receberá a recompensa segundo o que pretendia.",
    reference = "Sahih al-Bukhari (Hadith nº 1), Sahih Muslim (Hadith nº 1907)",
    category = "Hadith Fundamental"
)

val PROPHETS_LIST = listOf(
    ProphetStory(
        name = "Muhammad ﷺ",
        arabicName = "مُحَمَّدٌ رَسُولُ اللَّهِ",
        era = "Século VI - VII d.C. (Meca e Medina)",
        summary = "O último dos Profetas e Mensageiros, enviado como misericórdia para toda a humanidade. Trouxe o Nobre Alcorão, exemplificou o caráter mais sublime, ensinou a compaixão, a justiça e a adoração ao Criador Único.",
        keyLessons = listOf(
            "Misericórdia e perdão, mesmo aos que o perseguiram.",
            "Humildade constante e dedicação ao bem da comunidade.",
            "Honestidade impecável em todos os negócios e palavras."
        ),
        reference = "Qur'an 21:107; 33:40; 68:4"
    ),
    ProphetStory(
        name = "Ibrahim (Abraão) عليه السلام",
        arabicName = "إِبْرَاهِيمُ خَلِيلُ اللَّهِ",
        era = "Mesopotâmia e Península Arábica",
        summary = "O íntimo amigo de Allah (Khalilullah), pai dos profetas, reconstruiu a sagrada Kaaba com seu filho Ismail em Makkah e deu o exemplo supremo de submissão sincera e puro monoteísmo.",
        keyLessons = listOf(
            "Firmeza no Tawhid (adoração somente a Allah).",
            "Paciência absoluta diante de provações extremas.",
            "Uso da razão e do diálogo pacífico contra a idolatria."
        ),
        reference = "Qur'an 2:124-129; 16:120; 37:100-111"
    ),
    ProphetStory(
        name = "Musa (Moisés) عليه السلام",
        arabicName = "مُوسَى كَلِيمُ اللَّهِ",
        era = "Egito e Sinai",
        summary = "Aquele com quem Allah falou diretamente (Kalimullah). Libertou os Filhos de Israel da tirania do Faraó com milagres claros e recebeu a Torah como guia e luz.",
        keyLessons = listOf(
            "Coragem para enfrentar a tirania em defesa da justiça.",
            "Confiança inabalável em Allah quando os caminhos parecem fechados.",
            "Paciência com os defeitos e fraquezas de seu próprio povo."
        ),
        reference = "Qur'an 20:9-98; 26:10-68; 28:1-44"
    ),
    ProphetStory(
        name = "Isa (Jesus) عليه السلام",
        arabicName = "عِيسَى ابْنُ مَرْيَمَ",
        era = "Jerusalém e Galileia",
        summary = "Nobre mensageiro de Allah, nascido milagrosamente da Virgem Maria (Maryam), confirmou a Torah e trouxe o Evangelho (Injil). Curou os doentes com a permissão de Allah e pregou o desapego e o amor a Deus.",
        keyLessons = listOf(
            "Nobreza moral e compaixão para com os pobres e necessitados.",
            "Pureza espiritual e respeito sagrado à sua mãe Maryam.",
            "Testemunho sincero de que adorava unicamente a Allah, seu Senhor."
        ),
        reference = "Qur'an 3:45-59; 5:110-120; 19:16-36"
    )
)

val ISLAMIC_QUIZ_QUESTIONS = listOf(
    QuizQuestion(
        id = 1,
        question = "Quantas orações obrigatórias diárias (Salah Fard) são prescritas no Islam?",
        options = listOf("3 orações", "5 orações", "7 orações", "10 orações"),
        correctIndex = 1,
        explanation = "As cinco orações prescritas são: Fajr, Dhuhr, Asr, Maghrib e Isha, estabelecidas durante a Noite da Ascensão (Al-Isra wal-Mi'raj).",
        authenticReference = "Sahih al-Bukhari nº 349, Sahih Muslim nº 162"
    ),
    QuizQuestion(
        id = 2,
        question = "Qual é o primeiro pilar do Islam?",
        options = listOf(
            "O Jejum de Ramadan",
            "A Peregrinação (Hajj)",
            "A Shahadah (Testemunho de Fé)",
            "O Zakat (Caridade obrigatória)"
        ),
        correctIndex = 2,
        explanation = "A Shahadah é a declaração sincera: 'Ash-hadu an la ilaha illallah wa ash-hadu anna Muhammadan rasulullah' (Testemunho que não há divindade digna de adoração exceto Allah, e que Muhammad é Seu Mensageiro).",
        authenticReference = "Sahih al-Bukhari nº 8, Sahih Muslim nº 16"
    ),
    QuizQuestion(
        id = 3,
        question = "Qual foi a primeira surah revelada do Nobre Alcorão ao Profeta Muhammad ﷺ na Caverna de Hira?",
        options = listOf(
            "Surah Al-Fatihah",
            "Surah Al-'Alaq (Iqra)",
            "Surah Al-Baqarah",
            "Surah Yasin"
        ),
        correctIndex = 1,
        explanation = "Os primeiros cinco versículos revelados foram da Surah Al-'Alaq (96:1-5), começando com a sublime ordem: 'Lê em nome do teu Senhor que criou!'",
        authenticReference = "Sahih al-Bukhari nº 3"
    ),
    QuizQuestion(
        id = 4,
        question = "Em que direção os muçulmanos se voltam para realizar as orações (Qibla)?",
        options = listOf(
            "Para Jerusalém",
            "Para o Nascer do Sol",
            "Para a Sagrada Kaaba em Makkah",
            "Para o Monte Sinai"
        ),
        correctIndex = 2,
        explanation = "A direção da Qibla é voltada precisamente para a Sagrada Kaaba na Mesquita Al-Haram, em Meca.",
        authenticReference = "Qur'an 2:144"
    ),
    QuizQuestion(
        id = 5,
        question = "Qual é o mês sagrado em que os muçulmanos jejuam do alvorecer ao pôr do sol?",
        options = listOf(
            "Rajab",
            "Muharram",
            "Ramadan",
            "Shawwal"
        ),
        correctIndex = 2,
        explanation = "O Ramadan é o nono mês do calendário islâmico, o mês no qual o Alcorão começou a ser revelado e no qual o jejum é obrigatório.",
        authenticReference = "Qur'an 2:185"
    ),
    QuizQuestion(
        id = 6,
        question = "O que significa 'Tawhid' no Islam?",
        options = listOf(
            "O jejum voluntário",
            "A unicidade absoluta e singularidade de Allah na adoração, soberania e nomes",
            "A oração noturna",
            "A ablução com areia limpa"
        ),
        correctIndex = 1,
        explanation = "Tawhid é o cerne do Islam: reconhecer e afirmar que Allah é Único sem associados, parceiros ou intermediários.",
        authenticReference = "Qur'an 112:1-4, Sahih al-Bukhari nº 7372"
    )
)
