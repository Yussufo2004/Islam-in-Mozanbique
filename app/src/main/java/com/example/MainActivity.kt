package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.CityLocation
import com.example.data.model.MOZAMBIQUE_CITIES
import com.example.ui.components.NoorBottomNavigationBar
import com.example.ui.components.NoorTopAppBar
import com.example.ui.screens.community.CommunityScreen
import com.example.ui.screens.daily.DailyPracticeScreen
import com.example.ui.screens.home.HomeScreen
import com.example.ui.screens.learn.LearnScreen
import com.example.ui.screens.profile.ProfileScreen
import com.example.ui.theme.IslamicGreenPrimary
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.IslamicViewModel
import com.example.viewmodel.LearnSection
import com.example.viewmodel.MainTab
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: IslamicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppScreen(viewModel = viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(viewModel: IslamicViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val selectedCity by viewModel.selectedCity.collectAsState()
    val userMessage by viewModel.userMessage.collectAsState()
    val learnSection by viewModel.learnSection.collectAsState()
    val selectedSurah by viewModel.selectedSurah.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showCityDialog by remember { mutableStateOf(false) }

    // Display user messages via Snackbar
    LaunchedEffect(userMessage) {
        userMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearMessage()
        }
    }

    // Root BackHandler: if on secondary tab (and not in a sub-section handled by LearnScreen), return to Home
    BackHandler(enabled = currentTab != MainTab.HOME && !(currentTab == MainTab.LEARN && (learnSection != LearnSection.OVERVIEW || selectedSurah != null))) {
        viewModel.setMainTab(MainTab.HOME)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("main_app_scaffold"),
        topBar = {
            NoorTopAppBar(
                cityName = selectedCity.name,
                onCityClick = { showCityDialog = true }
            )
        },
        bottomBar = {
            NoorBottomNavigationBar(
                currentTab = currentTab,
                onTabSelected = { tab -> viewModel.setMainTab(tab) }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentTab) {
                MainTab.HOME -> {
                    HomeScreen(
                        viewModel = viewModel,
                        onOpenCitySelector = { showCityDialog = true }
                    )
                }
                MainTab.LEARN -> {
                    LearnScreen(viewModel = viewModel)
                }
                MainTab.COMMUNITY -> {
                    CommunityScreen(viewModel = viewModel)
                }
                MainTab.DAILY -> {
                    DailyPracticeScreen(
                        viewModel = viewModel,
                        onOpenCitySelector = { showCityDialog = true }
                    )
                }
                MainTab.PROFILE -> {
                    ProfileScreen(
                        viewModel = viewModel,
                        onOpenCitySelector = { showCityDialog = true }
                    )
                }
            }
        }
    }

    // City Selection Dialog (Supports all Mozambique provinces and major cities)
    if (showCityDialog) {
        AlertDialog(
            onDismissRequest = { showCityDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = IslamicGreenPrimary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Selecione a sua Cidade",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
            },
            text = {
                Column {
                    Text(
                        text = "Horários de oração calculados com precisão astronómica para cada coordenada:",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 350.dp)
                    ) {
                        items(MOZAMBIQUE_CITIES) { city ->
                            val isSelected = city.name == selectedCity.name
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.selectCity(city)
                                        showCityDialog = false
                                    }
                                    .padding(vertical = 10.dp, horizontal = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = city.name,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                        )
                                    )
                                    Text(
                                        text = city.country,
                                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selecionada",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showCityDialog = false }) {
                    Text("Concluir")
                }
            }
        )
    }
}
