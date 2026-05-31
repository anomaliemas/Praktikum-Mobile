package com.example.humasdirectorycompose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import timber.log.Timber

// Definisi Warna
val ColorBackground = Color(0xFFB6D6E1)
val ColorCard = Color(0xFFD8E4E9)
val ColorInstagram = Color(0xFF5E9387)
val ColorDetail = Color(0xFF002D52)

@Composable
fun HomeScreen(viewModel: ProkerViewModel, onNavigateToDetail: (String) -> Unit) {
    val prokerList by viewModel.prokerList.collectAsState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .padding(16.dp)
    ) {
        Text(
            text = "Program Unggulan",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(prokerList) { proker ->
                ProkerCard(
                    proker = proker,
                    modifier = Modifier.width(340.dp),
                    onDetailClick = {
                        viewModel.logDetailClick(proker.name)
                        onNavigateToDetail(proker.name)
                    },
                    onInstaClick = {
                        viewModel.logExplicitIntent(proker.name)
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(proker.link)))
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Daftar Program Kerja",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(prokerList) { proker ->
                ProkerCard(
                    proker = proker,
                    modifier = Modifier.fillMaxWidth(),
                    onDetailClick = {
                        viewModel.logDetailClick(proker.name)
                        onNavigateToDetail(proker.name)
                    },
                    onInstaClick = {
                        viewModel.logExplicitIntent(proker.name)
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(proker.link)))
                    }
                )
            }
        }
    }
}

@Composable
fun ProkerCard(proker: Proker, modifier: Modifier, onDetailClick: () -> Unit, onInstaClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = ColorCard),
        modifier = modifier
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Image(
                painter = painterResource(id = proker.photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(100.dp).clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = proker.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = proker.description,
                    fontSize = 12.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onInstaClick,
                        colors = ButtonDefaults.buttonColors(containerColor = ColorInstagram),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("INSTAGRAM", fontSize = 11.sp, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onDetailClick,
                        colors = ButtonDefaults.buttonColors(containerColor = ColorDetail),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text("DETAIL", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(prokerName: String, viewModel: ProkerViewModel, onBackClick: () -> Unit) {
    val prokerList by viewModel.prokerList.collectAsState()
    val data = prokerList.find { it.name == prokerName }
    val context = LocalContext.current

    data?.let {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .padding(8.dp)
                                .background(Color.Black.copy(alpha = 0.4f), shape = CircleShape)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Kembali",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.White
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            ) {
                // Gambar Hero yang ditarik penuh ke atas
                Image(
                    painter = painterResource(id = it.photo),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .offset(y = (-paddingValues.calculateTopPadding())),
                    contentScale = ContentScale.Crop
                )

                // Container teks
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .offset(y = (-paddingValues.calculateTopPadding() + 16.dp))
                ) {
                    Text(
                        text = it.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Tanggal: ${it.date}",
                        fontSize = 14.sp,
                        color = Color(0xFF475569)
                    )
                    Text(
                        text = "PJ: ${it.pj}",
                        fontSize = 14.sp,
                        color = ColorInstagram,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 16.dp),
                        thickness = 1.dp,
                        color = Color(0xFFE2E8F0)
                    )

                    Text(
                        text = it.description,
                        fontSize = 14.sp,
                        color = Color(0xFF334155),
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            Timber.i("Tombol Explicit Intent ditekan untuk Instagram PJ")
                            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.link)))
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ColorInstagram)
                    ) {
                        Text("KUNJUNGI INSTAGRAM PJ", fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
                    }
                }
            }
        }
    }
}