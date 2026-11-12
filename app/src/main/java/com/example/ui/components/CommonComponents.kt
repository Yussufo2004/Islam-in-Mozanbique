package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.MainTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoorTopAppBar(
    cityName: String,
    onCityClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.testTag("noor_top_app_bar"),
        title = {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Islam in Mozambique 🇲🇿",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                Text(
                    text = "Histórias e Lições Islâmicas",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = IslamicGoldDark,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        },
        actions = {
            // City selector badge
            Surface(
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable { onCityClick() }
                    .testTag("city_selector_chip"),
                color = MaterialTheme.colorScheme.primaryContainer,
                border = BorderStroke(1.dp, IslamicGoldLight.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Localização",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = cityName,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun NoorBottomNavigationBar(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
            .navigationBarsPadding()
            .testTag("main_bottom_nav"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 6.dp
    ) {
        val tabs = listOf(
            Triple(MainTab.HOME, Icons.Filled.Home, Icons.Outlined.Home),
            Triple(MainTab.LEARN, Icons.Filled.MenuBook, Icons.Outlined.MenuBook),
            Triple(MainTab.COMMUNITY, Icons.Filled.Groups, Icons.Outlined.Groups),
            Triple(MainTab.DAILY, Icons.Filled.AccessTime, Icons.Outlined.AccessTime),
            Triple(MainTab.PROFILE, Icons.Filled.Person, Icons.Outlined.Person)
        )

        tabs.forEach { (tab, filledIcon, outlinedIcon) ->
            val selected = currentTab == tab
            NavigationBarItem(
                selected = selected,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (selected) filledIcon else outlinedIcon,
                        contentDescription = tab.title
                    )
                },
                label = {
                    Text(
                        text = tab.title,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
fun IslamicOrnamentDivider(
    modifier: Modifier = Modifier,
    text: String = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = IslamicGoldPrimary,
            fontSize = 17.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier
                .width(160.dp)
                .padding(top = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = IslamicGoldLight.copy(alpha = 0.5f)
            )
            Text(
                text = " ✦ ",
                color = IslamicGoldPrimary,
                fontSize = 11.sp
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 1.dp,
                color = IslamicGoldLight.copy(alpha = 0.5f)
            )
        }
    }
}
