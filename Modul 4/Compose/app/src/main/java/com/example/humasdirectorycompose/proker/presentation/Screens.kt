package com.example.humasdirectorycompose.proker.presentation

import com.example.humasdirectorycompose.proker.data.Proker
import com.example.humasdirectorycompose.proker.data.ProkerData
import androidx.compose.foundation.lazy.items
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
fun HomeScreen(viewModel: ProkerViewModel, onNavigateToDetail: (String) -> Unit) {
    val prokerList by viewModel.prokerList.collectAsState()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFA2D2E2))) {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
            item {
                Text("Program Unggulan", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF02181E))
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(prokerList.take(3)) { proker ->
                        ProkerItem(
                            proker = proker,
                            modifier = Modifier.width(310.dp),
                            viewModel = viewModel,
                            onDetailClick = { onNavigateToDetail(proker.name) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
                Text("Daftar Program Kerja", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF02181E))
                Spacer(modifier = Modifier.height(12.dp))
            }
            items(prokerList) { proker ->
                ProkerItem(
                    proker = proker,
                    viewModel = viewModel,
                    onDetailClick = { onNavigateToDetail(proker.name) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ProkerItem(proker: Proker, modifier: Modifier = Modifier, viewModel: ProkerViewModel, onDetailClick: () -> Unit) {
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
                    modifier = Modifier.clip(RoundedCornerShape(16.dp)).size(80.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = proker.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF02181E))
                    Text(text = proker.description, maxLines = 2, overflow = TextOverflow.Ellipsis, fontSize = 12.sp, color = Color(0xFF022F56))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        viewModel.logExplicitClick()
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(proker.link)))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF488D84)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Instagram", fontSize = 11.sp, color = Color.White)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = onDetailClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF022F56)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Detail", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun DetailScreen(proker: Proker, viewModel: ProkerViewModel, onBack: () -> Unit) {
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
                    onClick = {
                        viewModel.logExplicitClick()
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(proker.link)))
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF488D84)),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text("Kunjungi Instagram PJ", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
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