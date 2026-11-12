package com.example.ui.screens.learn

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.IslamicOrnamentDivider
import com.example.ui.theme.*
import com.example.viewmodel.IslamicViewModel
import com.example.viewmodel.LearnSection

@Composable
fun LearnScreen(
    viewModel: IslamicViewModel,
    modifier: Modifier = Modifier
) {
    val learnSection by viewModel.learnSection.collectAsState()
    val selectedSurah by viewModel.selectedSurah.collectAsState()

    // Handle Android system back press to return to Learn Overview
    BackHandler(enabled = learnSection != LearnSection.OVERVIEW || selectedSurah != null) {
        if (selectedSurah != null) {
            viewModel.selectSurah(null)
        } else {
            viewModel.setLearnSection(LearnSection.OVERVIEW)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("learn_screen")
    ) {
        when {
            selectedSurah != null -> {
                SurahReaderView(
                    surah = selectedSurah!!,
                    onBack = { viewModel.selectSurah(null) }
                )
            }
            learnSection == LearnSection.OVERVIEW -> {
                LearnOverviewView(
                    onSelectSection = { section -> viewModel.setLearnSection(section) }
                )
            }
            learnSection == LearnSection.QURAN -> {
                QuranSurahsView(
                    onSelectSurah = { surah -> viewModel.selectSurah(surah) },
                    onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) }
                )
            }
            learnSection == LearnSection.SALAH -> {
                SalahGuideView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.TAHARAH -> {
                TaharahView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.AQIDAH -> {
                AqidahView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.DUAS -> {
                DuasLibraryView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.DHIKR -> {
                DhikrVirtuesView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.PROPHETS -> {
                ProphetsView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.AKHLAQ -> {
                AkhlaqView(onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
            learnSection == LearnSection.QUIZ -> {
                IslamicQuizView(viewModel = viewModel, onBack = { viewModel.setLearnSection(LearnSection.OVERVIEW) })
            }
        }
    }
}

@Composable
fun LearnOverviewView(
    onSelectSection: (LearnSection) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 6.dp)) {
                Text(
                    text = "Biblioteca de Conhecimento",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = "Aprenda as bases autênticas do Islam com referências verificadas no Nobre Alcorão e Sunnah.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        val categories = listOf(
            Triple(LearnSection.QURAN, Icons.Default.AutoStories, "Alcorão com texto em árabe, transcrição fonética e tradução em português."),
            Triple(LearnSection.SALAH, Icons.Default.Mosque, "Guia passo a passo ilustrado com as posturas, intenção e recitações."),
            Triple(LearnSection.TAHARAH, Icons.Default.WaterDrop, "Purificação: Wudu (ablução), Ghusl e o que invalida a purificação."),
            Triple(LearnSection.AQIDAH, Icons.Default.Shield, "Os 6 Pilares do Iman (Fé), Tawhid e o propósito da criação."),
            Triple(LearnSection.DUAS, Icons.Default.MenuBook, "Súplicas autênticas do quotidiano extraídas de Hisnul Muslim."),
            Triple(LearnSection.DHIKR, Icons.Default.TouchApp, "A virtude de recordar Allah e as melhores palavras de adoração."),
            Triple(LearnSection.PROPHETS, Icons.Default.HistoryEdu, "Histórias e lições de vida dos Nobres Profetas de Deus."),
            Triple(LearnSection.AKHLAQ, Icons.Default.Favorite, "Ética, caridade, respeito à família e virtudes do bom muçulmano."),
            Triple(LearnSection.QUIZ, Icons.Default.Quiz, "Teste e aprofunde o seu conhecimento com o Quiz Islâmico interativo.")
        )

        items(categories) { (section, icon, desc) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectSection(section) }
                    .testTag("learn_cat_${section.name.lowercase()}"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (section == LearnSection.QUIZ) IslamicGoldContainer
                                else MaterialTheme.colorScheme.primaryContainer
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (section == LearnSection.QUIZ) IslamicGoldDark else MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = section.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Abrir",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun QuranSurahsView(
    onSelectSurah: (Surah) -> Unit,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Nobre Qur'an",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }

        // Ayat al-Kursi feature card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "DESTAQUE ESPECIAL",
                            color = IslamicGoldLight,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "آيَةُ الْكُرْسِيِّ",
                            color = IslamicGoldPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Ayat al-Kursi (Versículo do Trono)",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Surah Al-Baqarah (2:255) • O maior versículo revelado",
                        color = IslamicCreamBg,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        item {
            Text(
                text = "Surahs Fundamentais",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        items(AUTHENTIC_SURAHS) { surah ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSelectSurah(surah) }
                    .testTag("surah_item_${surah.number}"),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = surah.number.toString(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = surah.nameTransliteration,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${surah.translationPortuguese} • ${surah.totalAyahs} versículos (${surah.revelationType})",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Text(
                        text = surah.nameArabic,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Composable
fun SurahReaderView(
    surah: Surah,
    onBack: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("surah_reader_screen"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Surah ${surah.nameTransliteration}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "${surah.translationPortuguese} • ${surah.totalAyahs} versículos",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        item {
            IslamicOrnamentDivider(text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ")
        }

        items(surah.verses) { verse ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${verse.number}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = verse.textArabic,
                        fontSize = 22.sp,
                        lineHeight = 36.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = verse.textTransliteration,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = verse.textPortuguese,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                }
            }
        }
    }
}

@Composable
fun SalahGuideView(onBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Como Realizar Salah",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Guia prático e autêntico de cada passo da oração",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "Condições prévias da Oração (Shurut)",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "1. Estar em estado de pureza (Wudu/Ablução).\n2. Estar na hora correta da oração.\n3. Cobrir a 'Awrah (vestuário modesto).\n4. Estar virado para a Qibla (direção da Kaaba).\n5. Formular a Niyyah (intenção sincera no coração).",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                    )
                }
            }
        }

        items(SALAH_GUIDE_STEPS) { step ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${step.stepNumber}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = step.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = step.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = IslamicMintSoft,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "O que recitar:",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = IslamicGreenDark
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step.arabicRecitation,
                                fontSize = 18.sp,
                                color = IslamicGreenPrimary,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = step.transliteration,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "«${step.translation}»",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TaharahView(onBack: () -> Unit) {
    val wuduSteps = listOf(
        "1. Intenção no coração e dizer: 'Bismillah' (Em nome de Allah).",
        "2. Lavar as duas mãos até aos pulsos 3 vezes.",
        "3. Enxaguar a boca (Madmadah) 3 vezes.",
        "4. Inalar água suavemente pelas narinas e assoar (Istinshaq) 3 vezes.",
        "5. Lavar todo o rosto (da linha do cabelo ao queixo e de orelha a orelha) 3 vezes.",
        "6. Lavar os braços dos dedos até aos cotovelos 3 vezes (iniciando pelo direito).",
        "7. Passar as mãos molhadas sobre a cabeça, do início à nuca e retorno (1 vez).",
        "8. Limpar as orelhas por dentro com o indicador e por fora com o polegar (1 vez).",
        "9. Lavar os pés até aos tornozelos 3 vezes, passando entre os dedos (iniciando pelo direito)."
    )

    val nullifiers = listOf(
        "Tudo o que sai pelas duas vias naturais (urina, fezes, gases).",
        "Sono profundo no qual se perde a perceção e o controlo.",
        "Perda de consciência (desmaio, anestesia total).",
        "Tocar diretamente os órgãos genitais sem barreira com desejo."
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Taharah (Purificação)",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "A chave para a aceitação da oração",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Passos do Wudu (Ablução)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    wuduSteps.forEach { step ->
                        Text(
                            text = step,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Referência: Qur'an 5:6; Sahih al-Bukhari nº 159",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = IslamicGoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "O que Invalida o Wudu",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    nullifiers.forEach { item ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text(text = "• ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text(text = item, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AqidahView(onBack: () -> Unit) {
    val pillars = listOf(
        AqidahPillar(1, "Crença em Allah", "الإِيمَانُ بِاللَّهِ", "Acreditar na Sua Unicidade absoluta: Criador de tudo, Único merecedor de adoração, dotado dos mais belos atributos.", "Qur'an 112:1-4"),
        AqidahPillar(2, "Crença nos Seus Anjos", "الإِيمَانُ بِالْمَلَائِكَةِ", "Criados da luz, obedientes sem pecado, incluindo Jibril (Gabriel), Mika'il e Israfil.", "Qur'an 2:285"),
        AqidahPillar(3, "Crença nos Livros Revelados", "الإِيمَانُ بِالْكُتُبِ", "A Torah (dada a Musa), os Salmos (a Dawud), o Evangelho (a Isa) e o Alcorão (o Livro final preservado).", "Qur'an 4:136"),
        AqidahPillar(4, "Crença nos Seus Mensageiros", "الإِيمَانُ بِالرُّسُلِ", "Profetas enviados a cada povo, sendo o Profeta Muhammad ﷺ o selo final dos profetas.", "Qur'an 21:107"),
        AqidahPillar(5, "Crença no Dia do Juízo", "الإِيمَانُ بِالْيَوْمِ الآخِرِ", "Ressurreição de todos os seres para prestação de contas, justiça divina e recompensa do Paraíso.", "Qur'an 22:7"),
        AqidahPillar(6, "Crença no Destino (Al-Qadr)", "الإِيمَانُ بِالْقَدَرِ", "Conhecimento e sabedoria perfeitos de Deus sobre tudo o que aconteceu e acontecerá, seja bom ou mau.", "Qur'an 54:49")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Aqidah (Os 6 Pilares da Fé)",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Fundamentos da crença islâmica canónica",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        items(pillars) { p ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${p.number}. ${p.name}",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = p.arabicName,
                            color = IslamicGoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = p.explanation,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Fonte: ${p.quranicReference}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = IslamicGreenMedium,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun DuasLibraryView(onBack: () -> Unit) {
    var selectedCat by remember { mutableStateOf("Todos") }
    val categories = listOf("Todos", "Quotidiano", "Proteção", "Refeição", "Paz e Alívio")

    val filtered = if (selectedCat == "Todos") AUTHENTIC_DUAS else AUTHENTIC_DUAS.filter { it.category == selectedCat }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Duas do Quotidiano",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Súplicas autênticas do Profeta ﷺ (Hisnul Muslim)",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(categories) { cat ->
                    FilterChip(
                        selected = selectedCat == cat,
                        onClick = { selectedCat = cat },
                        label = { Text(cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        items(filtered) { dua ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = dua.title,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Ocasião: ${dua.situation}",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = dua.arabic,
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.End,
                        lineHeight = 28.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = dua.transliteration,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "«${dua.translation}»",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Ref: ${dua.reference}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = IslamicGoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun DhikrVirtuesView(onBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Virtudes do Dhikr",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "A paz dos corações na lembrança de Allah",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "أَلَا بِذِكْرِ اللَّهِ تَطْمَئِنُّ الْقُلُوبُ",
                        color = IslamicGoldLight,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "«Não é porventura na lembrança de Allah que os corações encontram sossego?»",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Surah Ar-Ra'd (13:28)",
                        color = IslamicCreamBg,
                        style = MaterialTheme.typography.labelSmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        item {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "As Quatro Frases Mais Amadas por Allah",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "O Mensageiro de Allah ﷺ disse: 'As palavras mais queridas por Allah são quatro: SubhanAllah (Glorificado seja Allah), Alhamdulillah (Louvado seja Allah), La ilaha illallah (Não há divindade além de Allah) e Allahu Akbar (Allah é o Maior).'",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Fonte: Sahih Muslim nº 2137",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = IslamicGreenMedium,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun ProphetsView(onBack: () -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "História dos Profetas",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Exemplos de fé, paciência e retidão",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        items(PROPHETS_LIST) { p ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = p.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = p.arabicName,
                            color = IslamicGoldPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Text(
                        text = p.era,
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = p.summary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Lições Principais:",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    p.keyLessons.forEach { lesson ->
                        Text(
                            text = "• $lesson",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 6.dp, top = 2.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Referências: ${p.reference}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = IslamicGoldDark,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun AkhlaqView(onBack: () -> Unit) {
    val items = listOf(
        Pair("Honrar e Tratar Bem os Pais", "«E teu Senhor decretou que não adoreis senão a Ele, e que sejais bondosos para com vossos pais.» (Qur'an 17:23). O Profeta ﷺ ensinou que o agrado de Allah está no agrado dos pais."),
        Pair("Sinceridade e Honestidade nas Palavras", "O Profeta ﷺ disse: 'A verdade conduz à piedade, e a piedade conduz ao Paraíso. Um homem continua a falar a verdade até ser registrado junto a Allah como veraz.' (Sahih al-Bukhari nº 6094)."),
        Pair("Respeito e Assistência aos Vizinhos", "O Profeta ﷺ disse: 'Por Aquele em Cuja Mão está minha alma, não crê verdadeiramente quem dorme saciado enquanto o seu vizinho ao lado passa fome e ele o sabe.' (Al-Adab Al-Mufrad nº 112)."),
        Pair("O Sorriso e a Caridade", "O Profeta ﷺ disse: 'O teu sorriso para o teu irmão é uma caridade (Sadaqah).' (Jami' at-Tirmidhi nº 1956).")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Bom Comportamento (Akhlaq)",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "A essência do nobre caráter no Islam",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        items(items) { (title, desc) ->
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = desc, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

@Composable
fun IslamicQuizView(
    viewModel: IslamicViewModel,
    onBack: () -> Unit
) {
    val currentIndex by viewModel.quizCurrentIndex.collectAsState()
    val selectedOption by viewModel.quizSelectedOption.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val finished by viewModel.quizFinished.collectAsState()

    val totalQuestions = ISLAMIC_QUIZ_QUESTIONS.size
    val currentQuestion = ISLAMIC_QUIZ_QUESTIONS.getOrNull(currentIndex)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("islamic_quiz_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                IconButton(onClick = onBack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = "Quiz Islâmico Interativo",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = "Perguntas com fundamentação canónica verificada",
                        style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            }
        }

        if (finished) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = null,
                            tint = IslamicGoldDark,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "MashaAllah! Parabéns!",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Acertaste $score de $totalQuestions perguntas.",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = { viewModel.restartQuiz() },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Repetir Quiz")
                        }
                    }
                }
            }
        } else if (currentQuestion != null) {
            item {
                LinearProgressIndicator(
                    progress = { (currentIndex + 1).toFloat() / totalQuestions.toFloat() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Pergunta ${currentIndex + 1} de $totalQuestions",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Pontos: $score",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = currentQuestion.question,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            itemsIndexed(currentQuestion.options) { index, option ->
                val isSelected = selectedOption == index
                val hasAnswered = selectedOption != null
                val isCorrect = index == currentQuestion.correctIndex

                val containerColor = when {
                    !hasAnswered -> MaterialTheme.colorScheme.surface
                    isCorrect -> IslamicMintContainer
                    isSelected && !isCorrect -> Color(0xFFFFDCDD)
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    !hasAnswered -> MaterialTheme.colorScheme.outline
                    isCorrect -> IslamicGreenPrimary
                    isSelected && !isCorrect -> Color.Red
                    else -> MaterialTheme.colorScheme.outline
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = !hasAnswered) {
                            viewModel.answerQuizQuestion(index)
                        }
                        .testTag("quiz_option_$index"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = containerColor),
                    border = BorderStroke(1.dp, borderColor)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isSelected || isCorrect && hasAnswered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "${('A' + index)}",
                                    color = if (isSelected || isCorrect && hasAnswered) Color.White else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                            modifier = Modifier.weight(1f)
                        )
                        if (hasAnswered) {
                            if (isCorrect) {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Correto",
                                    tint = IslamicGreenPrimary
                                )
                            } else if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Incorreto",
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }

            if (selectedOption != null) {
                item {
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Explicação & Referência:",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentQuestion.explanation,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Fonte: ${currentQuestion.authenticReference}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = IslamicGoldDark,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }

                item {
                    Button(
                        onClick = { viewModel.nextQuizQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .testTag("quiz_next_button"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(if (currentIndex + 1 < totalQuestions) "Próxima Pergunta" else "Concluir Quiz")
                    }
                }
            }
        }
    }
}
