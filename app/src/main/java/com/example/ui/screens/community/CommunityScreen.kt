package com.example.ui.screens.community

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.theme.*
import com.example.viewmodel.IslamicViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    viewModel: IslamicViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val posts by viewModel.posts.collectAsState()
    val selectedGroupId by viewModel.selectedGroupId.collectAsState()
    val activeCommentPost by viewModel.activeCommentPost.collectAsState()
    val showCreateDialog by viewModel.showCreatePostDialog.collectAsState()
    val reportingPostId by viewModel.reportingPostId.collectAsState()
    val showRules by viewModel.showCommunityRules.collectAsState()

    val filteredPosts = if (selectedGroupId == null) posts else posts.filter { it.groupId == selectedGroupId }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("community_screen"),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.openCreatePost(true) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.testTag("create_post_fab")
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Nova Publicação")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Header with community title & rules button
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Comunidade Fraterna",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(
                            text = "Espaço aberto e respeitoso para todos",
                            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }

                    OutlinedButton(
                        onClick = { viewModel.openCommunityRules(true) },
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        border = BorderStroke(1.dp, IslamicGoldPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = IslamicGoldDark,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Regras",
                            color = IslamicGoldDark,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            // Group Chips
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedGroupId == null,
                            onClick = { viewModel.filterByGroup(null) },
                            label = { Text("Todos os Grupos") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                    items(INITIAL_GROUPS) { group ->
                        FilterChip(
                            selected = selectedGroupId == group.id,
                            onClick = { viewModel.filterByGroup(group.id) },
                            label = { Text("${group.iconEmoji} ${group.name}") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                selectedLabelColor = Color.White
                            )
                        )
                    }
                }
            }

            // Posts list
            if (filteredPosts.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.ChatBubbleOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Nenhuma publicação neste grupo ainda.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                        TextButton(onClick = { viewModel.openCreatePost(true) }) {
                            Text("Seja o primeiro a publicar!")
                        }
                    }
                }
            } else {
                items(filteredPosts, key = { it.id }) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .testTag("post_card_${post.id}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Author Header
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = post.authorName.take(1),
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text(
                                                text = post.authorName,
                                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                                            )
                                            Spacer(modifier = Modifier.width(6.dp))
                                            Surface(
                                                shape = RoundedCornerShape(6.dp),
                                                color = IslamicMintSoft
                                            ) {
                                                Text(
                                                    text = post.authorRole,
                                                    fontSize = 10.sp,
                                                    color = IslamicGreenDark,
                                                    fontWeight = FontWeight.SemiBold,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                        Text(
                                            text = "${post.authorCity} • ${post.timestampFormatted}",
                                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        )
                                    }
                                }

                                var showPostMenu by remember { mutableStateOf(false) }
                                Box {
                                    IconButton(
                                        onClick = { showPostMenu = true },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.MoreVert,
                                            contentDescription = "Opções",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = showPostMenu,
                                        onDismissRequest = { showPostMenu = false }
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Guardar publicação") },
                                            onClick = {
                                                viewModel.toggleSavePost(post.id)
                                                showPostMenu = false
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = if (post.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                                    contentDescription = null
                                                )
                                            }
                                        )
                                        DropdownMenuItem(
                                            text = { Text("Denunciar conteúdo") },
                                            onClick = {
                                                viewModel.openReportDialog(post.id)
                                                showPostMenu = false
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = Icons.Outlined.Flag,
                                                    contentDescription = null,
                                                    tint = Color.Red
                                                )
                                            }
                                        )
                                    }
                                }
                            }

                            // Group tag
                            Spacer(modifier = Modifier.height(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant
                            ) {
                                Text(
                                    text = post.groupName,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }

                            // Title & Content
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = post.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = post.content,
                                style = MaterialTheme.typography.bodyMedium,
                                lineHeight = 22.sp
                            )

                            // Action buttons row
                            Spacer(modifier = Modifier.height(14.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Like
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { viewModel.toggleLikePost(post.id) }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                            .testTag("like_button_${post.id}")
                                    ) {
                                        Icon(
                                            imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                            contentDescription = "Gostar",
                                            tint = if (post.isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${post.likesCount}",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = if (post.isLiked) FontWeight.Bold else FontWeight.Normal,
                                                color = if (post.isLiked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(12.dp))

                                    // Comments
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .clickable { viewModel.openCommentsFor(post) }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                            .testTag("comments_button_${post.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.ChatBubbleOutline,
                                            contentDescription = "Comentar",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = "${post.comments.size}",
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        )
                                    }
                                }

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    // Save
                                    IconButton(
                                        onClick = { viewModel.toggleSavePost(post.id) },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = if (post.isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                            contentDescription = "Guardar",
                                            tint = if (post.isSaved) IslamicGoldPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    // Share
                                    IconButton(
                                        onClick = {
                                            val shareIntent = Intent().apply {
                                                action = Intent.ACTION_SEND
                                                putExtra(Intent.EXTRA_TEXT, "${post.title}\n\n${post.content}\n\n— ${post.authorName} (${post.groupName})\nPartilhado via Islam in Mozambique 🇲🇿")
                                                type = "text/plain"
                                            }
                                            context.startActivity(Intent.createChooser(shareIntent, "Partilhar publicação"))
                                        },
                                        modifier = Modifier.size(36.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Outlined.Share,
                                            contentDescription = "Partilhar",
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal: Comments Bottom Sheet
    if (activeCommentPost != null) {
        val post = activeCommentPost!!
        var commentInput by remember { mutableStateOf("") }

        ModalBottomSheet(
            onDismissRequest = { viewModel.openCommentsFor(null) }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .imePadding()
            ) {
                Text(
                    text = "Comentários (${post.comments.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(12.dp))

                if (post.comments.isEmpty()) {
                    Text(
                        text = "Ainda não há comentários. Deixa a tua reflexão fraterna!",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f, fill = false)
                    ) {
                        items(post.comments) { c ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = c.authorName,
                                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                                        )
                                        Text(
                                            text = "${c.authorCity} • ${c.timestampFormatted}",
                                            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(text = c.text, style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Input row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = commentInput,
                        onValueChange = { commentInput = it },
                        placeholder = { Text("Escreva um comentário respeitoso...") },
                        modifier = Modifier.weight(1f),
                        maxLines = 3,
                        shape = RoundedCornerShape(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (commentInput.isNotBlank()) {
                                viewModel.addCommentToActive(commentInput)
                                commentInput = ""
                            }
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Enviar",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }

    // Dialog: Create New Post
    if (showCreateDialog) {
        var newTitle by remember { mutableStateOf("") }
        var newContent by remember { mutableStateOf("") }
        var selectedGroup by remember { mutableStateOf(INITIAL_GROUPS.first()) }
        var groupDropdownOpen by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { viewModel.openCreatePost(false) },
            title = {
                Text(
                    text = "Criar Nova Publicação",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Grupo de destino:",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Box {
                        OutlinedButton(
                            onClick = { groupDropdownOpen = true },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("${selectedGroup.iconEmoji} ${selectedGroup.name}")
                        }
                        DropdownMenu(
                            expanded = groupDropdownOpen,
                            onDismissRequest = { groupDropdownOpen = false }
                        ) {
                            INITIAL_GROUPS.forEach { g ->
                                DropdownMenuItem(
                                    text = { Text("${g.iconEmoji} ${g.name}") },
                                    onClick = {
                                        selectedGroup = g
                                        groupDropdownOpen = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Título da publicação") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = newContent,
                        onValueChange = { newContent = it },
                        label = { Text("Conteúdo / Reflexão") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        maxLines = 6
                    )

                    Text(
                        text = "Nota: Publicações de caracter religioso devem ter referências autênticas verificáveis.",
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.createPost(newTitle, newContent, selectedGroup)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("Publicar")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.openCreatePost(false) }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog: Report Content
    if (reportingPostId != null) {
        val reasons = listOf(
            "Conteúdo desrespeitoso ou ofensivo",
            "Informação religiosa sem fonte autêntica",
            "Spam ou publicidade comercial",
            "Discurso de ódio ou intolerância"
        )
        var selectedReason by remember { mutableStateOf(reasons.first()) }

        AlertDialog(
            onDismissRequest = { viewModel.openReportDialog(null) },
            title = { Text("Denunciar Conteúdo") },
            text = {
                Column {
                    Text(
                        text = "Ajude a manter a nossa comunidade fraterna, segura e autêntica. Indique o motivo:",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    reasons.forEach { r ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { selectedReason = r }
                                .padding(vertical = 6.dp)
                        ) {
                            RadioButton(
                                selected = selectedReason == r,
                                onClick = { selectedReason = r }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = r, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { viewModel.submitReport(selectedReason) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Enviar Denúncia")
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.openReportDialog(null) }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Dialog: Community Rules
    if (showRules) {
        AlertDialog(
            onDismissRequest = { viewModel.openCommunityRules(false) },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Directrizes da Comunidade")
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "1. Fraternidade e Respeito: Trate todos os irmãos e visitantes com educação, cordialidade e paciência (Hikmah).",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "2. Rigor Religioso: Todas as citações do Alcorão e Ahadith devem conter referências verificadas de fontes canónicas.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "3. Acolhimento a Interessados: Quem não é muçulmano é bem-vindo a fazer perguntas e aprender sem qualquer constrangimento.",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "4. Sem Disputas Estéreis: Evite polémicas divisórias ou debates sem benefício espiritual.",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            confirmButton = {
                Button(onClick = { viewModel.openCommunityRules(false) }) {
                    Text("Entendido")
                }
            }
        )
    }
}
