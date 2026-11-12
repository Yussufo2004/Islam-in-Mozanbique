package com.example.data.model

data class Surah(
    val number: Int,
    val nameArabic: String,
    val nameTransliteration: String,
    val translationPortuguese: String,
    val totalAyahs: Int,
    val revelationType: String, // Meca ou Medina
    val verses: List<Verse>
)

data class Verse(
    val number: Int,
    val textArabic: String,
    val textTransliteration: String,
    val textPortuguese: String
)

val AUTHENTIC_SURAHS = listOf(
    Surah(
        number = 1,
        nameArabic = "الفاتحة",
        nameTransliteration = "Al-Fatihah",
        translationPortuguese = "A Abertura",
        totalAyahs = 7,
        revelationType = "Meca",
        verses = listOf(
            Verse(
                number = 1,
                textArabic = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                textTransliteration = "Bismillāhi r-raḥmāni r-raḥīm",
                textPortuguese = "Em nome de Allah, o Clemente, o Misericordioso."
            ),
            Verse(
                number = 2,
                textArabic = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                textTransliteration = "Al-ḥamdu lillāhi rabbi l-ʿālamīn",
                textPortuguese = "Louvado seja Allah, Senhor do Universo."
            ),
            Verse(
                number = 3,
                textArabic = "الرَّحْمَٰنِ الرَّحِيمِ",
                textTransliteration = "Ar-raḥmāni r-raḥīm",
                textPortuguese = "O Clemente, o Misericordioso."
            ),
            Verse(
                number = 4,
                textArabic = "مَالِكِ يَوْمِ الدِّينِ",
                textTransliteration = "Māliki yawmi d-dīn",
                textPortuguese = "Soberano do Dia do Juízo."
            ),
            Verse(
                number = 5,
                textArabic = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
                textTransliteration = "Iyyāka naʿbudu wa-iyyāka nastaʿīn",
                textPortuguese = "Só a Ti adoramos e só de Ti imploramos ajuda."
            ),
            Verse(
                number = 6,
                textArabic = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
                textTransliteration = "Ihdinā ṣ-ṣirāṭa l-mustaqīm",
                textPortuguese = "Guia-nos à senda reta."
            ),
            Verse(
                number = 7,
                textArabic = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
                textTransliteration = "Ṣirāṭa lladhīna anʿamta ʿalayhim ghayri l-maghḍūbi ʿalayhim walā ḍ-ḍāllīn",
                textPortuguese = "À senda dos que agraciaste, não à dos que incorreram na Tua ira, nem à dos extraviados."
            )
        )
    ),
    Surah(
        number = 112,
        nameArabic = "الإخلاص",
        nameTransliteration = "Al-Ikhlas",
        translationPortuguese = "A Pureza da Fé",
        totalAyahs = 4,
        revelationType = "Meca",
        verses = listOf(
            Verse(
                number = 1,
                textArabic = "قُلْ هُوَ اللَّهُ أَحَدٌ",
                textTransliteration = "Qul huwa Allāhu aḥad",
                textPortuguese = "Dize: Ele é Allah, o Único!"
            ),
            Verse(
                number = 2,
                textArabic = "اللَّهُ الصَّمَدُ",
                textTransliteration = "Allāhu ṣ-ṣamad",
                textPortuguese = "Allah, o Absoluto, a Quem todos recorrem!"
            ),
            Verse(
                number = 3,
                textArabic = "لَمْ يَلِدْ وَلَمْ يُولَدْ",
                textTransliteration = "Lam yalid wa-lam yūlad",
                textPortuguese = "Jamais gerou, nem foi gerado."
            ),
            Verse(
                number = 4,
                textArabic = "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
                textTransliteration = "Wa-lam yakun lahū kufuwan aḥad",
                textPortuguese = "E ninguém Lhe é comparável."
            )
        )
    ),
    Surah(
        number = 113,
        nameArabic = "الفلق",
        nameTransliteration = "Al-Falaq",
        translationPortuguese = "A Alvorada",
        totalAyahs = 5,
        revelationType = "Meca",
        verses = listOf(
            Verse(
                number = 1,
                textArabic = "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ",
                textTransliteration = "Qul aʿūdhu bi-rabbi l-falaq",
                textPortuguese = "Dize: Amparo-me no Senhor da alvorada."
            ),
            Verse(
                number = 2,
                textArabic = "مِن شَرِّ مَا خَلَقَ",
                textTransliteration = "Min sharri mā khalaq",
                textPortuguese = "Do mal daquilo que Ele criou."
            ),
            Verse(
                number = 3,
                textArabic = "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ",
                textTransliteration = "Wa-min sharri ghāsiqin idhā waqab",
                textPortuguese = "E do mal das trevas da noite quando se adensam."
            ),
            Verse(
                number = 4,
                textArabic = "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ",
                textTransliteration = "Wa-min sharri n-naffāthāti fī l-ʿuqad",
                textPortuguese = "E do mal das que sopram nos nós."
            ),
            Verse(
                number = 5,
                textArabic = "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ",
                textTransliteration = "Wa-min sharri ḥāsidin idhā ḥasad",
                textPortuguese = "E do mal do invejoso quando inveja."
            )
        )
    ),
    Surah(
        number = 114,
        nameArabic = "الناس",
        nameTransliteration = "An-Nas",
        translationPortuguese = "A Humanidade",
        totalAyahs = 6,
        revelationType = "Meca",
        verses = listOf(
            Verse(
                number = 1,
                textArabic = "قُلْ أَعُوذُ بِرَبِّ النَّاسِ",
                textTransliteration = "Qul aʿūdhu bi-rabbi n-nās",
                textPortuguese = "Dize: Amparo-me no Senhor dos humanos."
            ),
            Verse(
                number = 2,
                textArabic = "مَلِكِ النَّاسِ",
                textTransliteration = "Maliki n-nās",
                textPortuguese = "O Soberano dos humanos."
            ),
            Verse(
                number = 3,
                textArabic = "إِلَٰهِ النَّاسِ",
                textTransliteration = "Ilāhi n-nās",
                textPortuguese = "O Deus dos humanos."
            ),
            Verse(
                number = 4,
                textArabic = "مِن شَرِّ الْوَسْوَاسِ الْخَنَّاسِ",
                textTransliteration = "Min sharri l-waswāsi l-khannās",
                textPortuguese = "Contra o mal do sussurrador insidioso e fugaz."
            ),
            Verse(
                number = 5,
                textArabic = "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ",
                textTransliteration = "Alladhī yuwaswisu fī ṣudūri n-nās",
                textPortuguese = "Que sussurra nos corações dos humanos."
            ),
            Verse(
                number = 6,
                textArabic = "مِنَ الْجِنَّةِ وَالنَّاسِ",
                textTransliteration = "Mina l-jinnati wan-nās",
                textPortuguese = "Seja ele dos gênios ou dos homens."
            )
        )
    ),
    Surah(
        number = 103,
        nameArabic = "العصر",
        nameTransliteration = "Al-'Asr",
        translationPortuguese = "O Tempo",
        totalAyahs = 3,
        revelationType = "Meca",
        verses = listOf(
            Verse(
                number = 1,
                textArabic = "وَالْعَصْرِ",
                textTransliteration = "Wal-ʿaṣr",
                textPortuguese = "Pelo tempo através das eras."
            ),
            Verse(
                number = 2,
                textArabic = "إِنَّ الْإِنسَانَ لَفِي خُسْرٍ",
                textTransliteration = "Inna l-insāna lafī khusr",
                textPortuguese = "Por certo, o ser humano está em perda."
            ),
            Verse(
                number = 3,
                textArabic = "إِلَّا الَّذِينَ آمَنُوا وَعَمِلُوا الصَّالِحَاتِ وَتَوَاصَوْا بِالْحَقِّ وَتَوَاصَوْا بِالصَّبْرِ",
                textTransliteration = "Illā lladhīna āmanū wa-ʿamilū ṣ-ṣāliḥāti wa-tawāṣaw bil-ḥaqqi wa-tawāṣaw biṣ-ṣabr",
                textPortuguese = "Exceto os que creem, praticam o bem, e se recomendam mutuamente a verdade e a paciência."
            )
        )
    ),
    Surah(
        number = 108,
        nameArabic = "الكوثر",
        nameTransliteration = "Al-Kawthar",
        translationPortuguese = "A Abundância",
        totalAyahs = 3,
        revelationType = "Meca",
        verses = listOf(
            Verse(
                number = 1,
                textArabic = "إِنَّا أَعْطَيْنَاكَ الْكَوْثَرَ",
                textTransliteration = "Innā aʿṭaynāka l-kawthar",
                textPortuguese = "Por certo, concedemos-te a Abundância."
            ),
            Verse(
                number = 2,
                textArabic = "فَصَلِّ لِرَبِّكَ وَانْحَرْ",
                textTransliteration = "Fa-ṣalli li-rabbika wan-ḥar",
                textPortuguese = "Reza, pois, ao teu Senhor, e faz sacrifício."
            ),
            Verse(
                number = 3,
                textArabic = "إِنَّ شَانِئَكَ هُوَ الْأَبْتَرُ",
                textTransliteration = "Inna shāni'aka huwa l-abtar",
                textPortuguese = "Por certo, o que te odeia é quem ficará sem descendência."
            )
        )
    )
)

data class AyatSpecial(
    val title: String,
    val surahName: String,
    val reference: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val virtues: String
)

val SPECIAL_AYATS = listOf(
    AyatSpecial(
        title = "Ayat al-Kursi (Versículo do Trono)",
        surahName = "Al-Baqarah",
        reference = "Qur'an 2:255",
        arabic = "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ ۗ مَن ذَا الَّذِي يَشْفَعُ عِندَهُ إِلَّا بِإِذْنِهِ ۚ يَعْلَمُ مَا بَيْنَ أَيْدِيهِمْ وَمَا خَلْفَهُمْ ۖ وَلَا يُحِيطُونَ بِشَيْءٍ مِّنْ عِلْمِهِ إِلَّا بِمَا شَاءَ ۚ وَسِعَ كُرْسِيُّهُ السَّمَاوَاتِ وَالْأَرْضَ ۖ وَلَا يَئُودُهُ حِفْظُهُمَا ۚ وَهُوَ الْعَلِيُّ الْعَظِيمُ",
        transliteration = "Allāhu lā ilāha illā huwal-ḥayyul-qayyūm, lā ta'khudhuhū sinatuw-walā nawm, lahū mā fis-samāwāti wamā fil-arḍ, man dhal-ladhī yashfa'u 'indahū illā bi'idhnih, ya'lamu mā bayna aydīhim wamā khalfahum, walā yuḥīṭūna bishay'im-min 'ilmihī illā bimā shā', wasi'a kursiyyuhus-samāwāti wal-arḍ, walā ya'ūduhū ḥifẓuhumā, wahuwal-'aliyyul-'aẓīm.",
        translation = "Allah! Não há divindade além d'Ele, o Vivente, o Sustentador de tudo. Não O toma nem sonolência nem sono. A Ele pertence tudo o que está nos céus e tudo o que está na terra. Quem poderá interceder perante Ele senão com Sua permissão? Ele sabe o que está diante deles e o que está por trás deles, e não abrangem nada do Seu conhecimento, senão o que Ele quiser. Seu Trono abrange os céus e a terra, e a conservação de ambos não O cansa. Ele é o Altíssimo, o Grandioso.",
        virtues = "O maior versículo do Qur'an. Quem o recita após cada oração obrigatória, nada o impedirá de entrar no Paraíso senão a morte (Sahih an-Nasa'i, Ibn Hibban)."
    )
)
