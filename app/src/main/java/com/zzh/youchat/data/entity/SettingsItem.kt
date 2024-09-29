package com.zzh.youchat.data.entity

sealed class SettingsItem {
    data class SwitchItem(val title:String, val isChecked:Boolean) : SettingsItem()
    data class ListItem(val title:String, val selectedItem:Int, val options:List<String>) : SettingsItem()
    data class TextItem(val title:String, val textValue:String) : SettingsItem()
}