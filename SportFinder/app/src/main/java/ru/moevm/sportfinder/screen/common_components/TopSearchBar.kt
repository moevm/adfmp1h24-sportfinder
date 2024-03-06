package ru.moevm.sportfinder.screen.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import ru.moevm.sportfinder.R
import ru.moevm.sportfinder.ui.theme.SportFinderLightColorScheme

@Composable
fun TopSearchBar(
    searchText: String,
    onTextSearchChanged: (String) -> Unit,
    onFilterApply: () -> Unit
) {

    val constraintsTopSearch = ConstraintSet {
        val searchTextField = createRefFor("searchTextField")
        val spacer = createRefFor("spacer")
        val searchButton = createRefFor("searchButton")

        constrain(searchTextField) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(spacer.start)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(spacer) {
            width = Dimension.value(4.dp)
            top.linkTo(parent.top)
            end.linkTo(searchButton.start)
        }
        constrain(searchButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
            width = Dimension.value(48.dp)
            height = Dimension.value(48.dp)
        }
    }
    ConstraintLayout(
        constraintSet = constraintsTopSearch,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, start = 8.dp, end = 8.dp)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("searchTextField")
                .height(48.dp),
            value = searchText,
            textStyle = TextStyle(
                color = SportFinderLightColorScheme.onPrimary,
                fontSize = 14.sp,
                lineHeight = 16.sp,
                ),
            onValueChange = onTextSearchChanged,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = SportFinderLightColorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = SportFinderLightColorScheme.onPrimary
            ),
            singleLine = true
        )


        IconButton(
            modifier = Modifier
                .background(SportFinderLightColorScheme.primary, shape = RoundedCornerShape(8.dp))
                .layoutId("searchButton")
                .fillMaxHeight(),
            onClick = onFilterApply
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(id = R.drawable.ic_sport_court_screen_search),
                tint = SportFinderLightColorScheme.onPrimary,
                contentDescription = null
            )
        }
    }
}

@Composable
@Preview
fun TopSearchBarPreview() {
    Box {
        TopSearchBar(searchText = "filter_text", {}, {})
    }
}

