package com.zzh.youchat.ui.component

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.youchat.R

@Composable
fun FloatingActionMenu(
    menuIcon: List<Painter>,
    menuText: List<String>,
    menuOnClick: List<() -> Unit>,
) {
    val iconSize = 24.dp
    val fontSize = 16.sp
    val lineHeight = 40.dp
    val menuItemNumber = menuIcon.size + 1

    var isMenuExpanded by remember { mutableStateOf(false) }
    val cornerRadius by animateDpAsState(if (isMenuExpanded) 12.dp else 16.dp)
    val menuWidth by animateDpAsState(if (!isMenuExpanded) 56.dp else 200.dp)
    val menuHeight by animateDpAsState(if (!isMenuExpanded) 56.dp else (lineHeight * menuItemNumber))


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
                        Modifier.clickable {
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
                Column {
                    // 菜单功能按钮
                    LazyColumn {
                        items(menuIcon.size) { index ->
                            Row(
                                modifier = Modifier
                                    .height(lineHeight)
                                    .fillMaxWidth()
                                    .clickable {
                                        menuOnClick[index]()
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(modifier = Modifier.size(20.dp))
                                Icon(
                                    painter = menuIcon[index],
                                    contentDescription = menuText[index],
                                    modifier = Modifier.size(iconSize),
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                                Box(modifier = Modifier.size(15.dp))
                                Text(menuText[index], fontSize = fontSize)
                                Box(modifier = Modifier.size(20.dp))
                            }
                        }
                    }

                    // 关闭菜单按钮
                    Row(
                        modifier = Modifier
                            .height(lineHeight)
                            .fillMaxWidth()
                            .clickable {
                                isMenuExpanded = false
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.size(20.dp))
                        Icon(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.close),
                            modifier = Modifier.size(iconSize),
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        )
                        Box(modifier = Modifier.size(15.dp))
                        Text(stringResource(R.string.close), fontSize = fontSize)
                        Box(modifier = Modifier.size(20.dp))
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
    FloatingActionMenu(
        listOf(
            painterResource(R.drawable.ic_add_person),
            painterResource(R.drawable.ic_add_group),
            painterResource(R.drawable.ic_create_group),
        ),
        listOf(
            stringResource(R.string.add_friend),
            stringResource(R.string.add_group),
            stringResource(R.string.create_group),
        ),
        listOf(
            {},
            {},
            {},
        )
    )
}
