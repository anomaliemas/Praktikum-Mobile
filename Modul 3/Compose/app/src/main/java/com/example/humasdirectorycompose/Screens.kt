package com.example.humasdirectorycompose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(onNavigateToDetail: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFA2D2E2))
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text("Program Unggulan", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF02181E))
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(ProkerData.listData.take(3)) { proker ->
                        ProkerItem(proker, modifier = Modifier.width(310.dp), onDetailClick = { onNavigateToDetail(proker.name) })
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text("Daftar Program Kerja", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF02181E))
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(ProkerData.listData) { proker ->
                ProkerItem(proker, onDetailClick = { onNavigateToDetail(proker.name) })
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProkerItem(proker: Proker, modifier: Modifier = Modifier, onDetailClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFCCDEE4)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = proker.photo),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = proker.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF02181E))
                    Text(text = proker.description, maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = 12.sp, color = Color(0xFF022F56))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), horizontalArrangement = Arrangement.End) {
                Button(
                    onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(proker.link))) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF488D84)),
                    shape = RoundedCornerShape(50.dp)
                ) { Text("Instagram", fontSize = 11.sp, color = Color.White) }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onDetailClick, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF022F56)), shape = RoundedCornerShape(50.dp)) {
                    Text("Detail", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DetailScreen(proker: Proker, onBack: () -> Unit) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            Image(
                painter = painterResource(id = proker.photo),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().height(300.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                Text(text = proker.name, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF02181E))
                Text(text = "Tanggal: ${proker.date}", fontSize = 14.sp, color = Color(0xFF022F56), modifier = Modifier.padding(top = 8.dp))
                Text(text = "PJ: ${proker.pj}", fontSize = 15.sp, color = Color(0xFF488D84), fontWeight = FontWeight.Medium, modifier = Modifier.padding(top = 4.dp, bottom = 16.dp))
                HorizontalDivider(color = Color(0xFFCCDEE4), thickness = 1.dp)
                Text(text = proker.description, fontSize = 15.sp, lineHeight = 24.sp, color = Color(0xFF022F56), modifier = Modifier.padding(vertical = 20.dp))
                Button(
                    onClick = { context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(proker.link))) },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF488D84)),
                    shape = RoundedCornerShape(50.dp)
                ) { Text("Kunjungi Instagram PJ", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold) }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
        IconButton(
            onClick = onBack,
            modifier = Modifier.padding(16.dp).align(Alignment.TopStart).background(Color.Black.copy(alpha = 0.3f), RoundedCornerShape(50.dp))
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
    }
}