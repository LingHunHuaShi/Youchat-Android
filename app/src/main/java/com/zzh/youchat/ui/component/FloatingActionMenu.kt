package com.zzh.youchat.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.youchat.R

@Composable
fun FloatingActionMenu(

) {
    val iconSize = 24.dp
    val fontSize = 16.sp
    val lineHeight = 40.dp

    var isMenuExpanded by remember { mutableStateOf(false) }
    val cornerRadius by animateDpAsState(if (isMenuExpanded) 28.dp else 16.dp)
    val menuWidth by animateDpAsState(if (!isMenuExpanded) 56.dp else 200.dp)
    val menuHeight by animateDpAsState(if (!isMenuExpanded) 56.dp else (lineHeight * 3 + 10.dp))

    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Surface(
            modifier = Modifier
                .height(menuHeight)
                .width(menuWidth)
                .clip(RoundedCornerShape(cornerRadius))
                .then(
                    if (!isMenuExpanded) {
                        Modifier.clickable{
                            isMenuExpanded = true
                        }
                    } else {
                        Modifier
                    }
                ),
            color = MaterialTheme.colorScheme.primaryContainer,
            shadowElevation = 8.dp
        ) {
            if (isMenuExpanded) {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(vertical = 5.dp, horizontal = 20.dp)
                ) {
                    Row(
                        modifier = Modifier.height(lineHeight)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_person),
                            contentDescription = stringResource(R.string.add_friend),
                            modifier = Modifier.size(iconSize),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Box(modifier = Modifier.size(15.dp))
                        Text(stringResource(R.string.add_friend), fontSize = fontSize)
                    }
                    Row(
                        modifier = Modifier.height(lineHeight)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add_group),
                            contentDescription = stringResource(R.string.add_group),
                            modifier = Modifier.size(iconSize),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Box(modifier = Modifier.size(15.dp))
                        Text(stringResource(R.string.add_group), fontSize = fontSize)
                    }
                    Row(
                        modifier = Modifier.height(lineHeight)
                            .fillMaxWidth()
                            .clickable{
                                isMenuExpanded = false
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.add_group),
                            modifier = Modifier.size(iconSize),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Box(modifier = Modifier.size(15.dp))
                        Text("关闭", fontSize = fontSize)
                    }
                }
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_add),
                    contentDescription = stringResource(R.string.add),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun FloatingActionMenuPreview() {
    FloatingActionMenu()
}
