package com.example.catapp.cat.presentation

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.catapp.core.common.UiState
import com.example.catapp.core.database.CatEntity

val NavyBlue = Color(0xFF37474F)
val BrickRed = Color(0xFFD4A373)
val LightBg = Color(0xFFF9F9F9)

@Composable
fun HomeScreen(viewModel: CatViewModel, onNavigateToDetail: (String) -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val userName by viewModel.userName.collectAsState()
    var inputText by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().background(LightBg)) {
        when (val state = uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NavyBlue) // Loading pakai Navy
                }
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    item {
                        Text("Hello $userName", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = NavyBlue)
                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = inputText,
                            onValueChange = { inputText = it },
                            label = { Text("Type your name", color = NavyBlue.copy(alpha = 0.7f)) },
                            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NavyBlue,
                                unfocusedBorderColor = NavyBlue.copy(alpha = 0.5f),
                                focusedLabelColor = NavyBlue
                            )
                        )
                        Button(
                            onClick = {
                                viewModel.saveName(inputText)
                                inputText = "" },
                            colors = ButtonDefaults.buttonColors(containerColor = BrickRed),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Change Name", color = Color.White, fontWeight = FontWeight.SemiBold)
                        }
                        Spacer(modifier = Modifier.height(24.dp))

                        Text("Popular Breeds", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = NavyBlue)
                        Spacer(modifier = Modifier.height(12.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.data.take(3)) { cat ->
                                CatItem(
                                    cat = cat,
                                    modifier = Modifier.width(310.dp),
                                    onDetailClick = { onNavigateToDetail(cat.id.toString()) }
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(28.dp))
                        Text("All Breeds", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = NavyBlue)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    items(state.data) { cat ->
                        CatItem(
                            cat = cat,
                            onDetailClick = { onNavigateToDetail(cat.id.toString()) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun CatItem(cat: CatEntity, modifier: Modifier = Modifier, onDetailClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                val imageUrl = cat.imageUrl

                AsyncImage(
                    model = imageUrl,
                    contentDescription = cat.name,
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = cat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = NavyBlue)
                    Text(text = cat.description, maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = 12.sp, color = Color.Gray)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${cat.name} Cat"))
                        context.startActivity(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = BrickRed),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Search", fontSize = 11.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onDetailClick,
                    colors = ButtonDefaults.buttonColors(containerColor = NavyBlue),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("View", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DetailScreen(catId: String, viewModel: CatViewModel, onBack: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    if (uiState is UiState.Success) {
        val cat = (uiState as UiState.Success).data.find { it.id.toString() == catId }
        if (cat != null) {
            Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
                Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                    AsyncImage(
                        model = cat.imageUrl,
                        contentDescription = cat.name,
                        modifier = Modifier.fillMaxWidth().height(300.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                        Text(text = cat.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = NavyBlue)

                        Text(text = "Purebred Cat", fontSize = 14.sp, color = NavyBlue.copy(alpha = 0.8f), modifier = Modifier.padding(top = 8.dp))
                        Text(text = "Category: Pet", fontSize = 15.sp, color = BrickRed, fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))

                        HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                        Text(text = cat.description, fontSize = 15.sp, lineHeight = 24.sp, color = Color.Black, modifier = Modifier.padding(vertical = 20.dp))

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${cat.name} Cat"))
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth().height(50.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = NavyBlue),
                            shape = RoundedCornerShape(50.dp)
                        ) {
                            Text("Find Out More", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.padding(16.dp).align(Alignment.TopStart).background(NavyBlue.copy(alpha = 0.5f), RoundedCornerShape(50.dp))
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
            }
        }
    }
}