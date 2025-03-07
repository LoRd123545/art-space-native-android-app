package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceApp(
                        modifier = Modifier
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}

class ImageDetails(@StringRes var title: Int, @StringRes var author: Int, @IntegerRes var year: Int)

val paintersArray: Array<Int> = arrayOf(
    R.drawable.colors,
    R.drawable.birds,
    R.drawable.ships,
    R.drawable.flowers
)

val painterToDetails : HashMap<Int, ImageDetails> = hashMapOf(
    R.drawable.colors to ImageDetails(
        author = R.string.colors_img_author,
        year = R.integer.colors_img_year,
        title = R.string.colors_img_title
    ),
    R.drawable.ships to ImageDetails(
        author = R.string.ships_img_author,
        year = R.integer.ships_img_year,
        title = R.string.ships_img_title
    ),
    R.drawable.flowers to ImageDetails(
        author = R.string.flowers_img_author,
        year = R.integer.flowers_img_year,
        title = R.string.flowers_img_title
    ),
    R.drawable.birds to ImageDetails(
        author = R.string.birds_img_author,
        year = R.integer.birds_img_year,
        title = R.string.birds_img_title
    )
)

@Composable
fun ArtSpaceApp(
    modifier: Modifier = Modifier
) {
    var index by remember {
        mutableStateOf(0)
    }

    @DrawableRes val currentPainter: Int = paintersArray[index]
    @StringRes val currentImgTitle = painterToDetails[currentPainter]!!.title
    @StringRes val currentImgAuthor = painterToDetails[currentPainter]!!.author
    @IntegerRes val currentImgYear = painterToDetails[currentPainter]!!.year

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageWithFrame(
            painter = currentPainter,
            contentDescription = stringResource(currentImgTitle),
            modifier = Modifier,
            frameWidth = 200.dp
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        ) {
            Info(
                author = stringResource(currentImgAuthor),
                year = integerResource(currentImgYear).toString(),
                title = stringResource(currentImgTitle),
                modifier = Modifier
            )
            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )
            NavButtons(
                onNextBtnClick = {
                    var temp = index + 1
                    temp %= paintersArray.size

                    index = temp
                },
                nextBtnText = stringResource(R.string.next_btn_text),
                onPrevBtnClick = {
                    var temp = index - 1
                    temp %= paintersArray.size

                    if(temp < 0) {
                        temp += paintersArray.size
                    }

                    index = temp
                },
                prevBtnText = stringResource(R.string.prev_btn_text),
            )
        }
    }
}

@Composable
fun ImageWithFrame(
    modifier: Modifier = Modifier,
    @DrawableRes painter: Int,
    contentDescription: String,
    frameWidth: Dp
) {
    Box(
        modifier = Modifier
            .width(frameWidth)
            .aspectRatio(3 / 4f)
            .background(color = Color.White)
    ) {
        Image(
            painter = painterResource(painter),
            contentDescription = contentDescription,
            contentScale = ContentScale.FillBounds
        )
    }

}

@Composable
fun NavButtons(
    modifier: Modifier = Modifier,
    onPrevBtnClick: () -> Unit,
    prevBtnText: String,
    onNextBtnClick: () -> Unit,
    nextBtnText: String
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onPrevBtnClick
        ) {
            Text(
                text = prevBtnText
            )
        }
        Button(
            onClick = onNextBtnClick
        ) {
            Text(
                text = nextBtnText
            )
        }
    }
}

@Composable
fun Info(
    modifier: Modifier = Modifier,
    title: String,
    author: String,
    year: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 32.sp
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Row {
            Text(
                text = author,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .width(8.dp)
            )
            Text(
                text = "($year)"
            )
        }
    }
}