# 程式設計期末專題開發紀錄
## 大綱
- 主題 : 飛機大戰
- 介紹 : 復刻飛機大戰遊戲，並且添加新功能

## 分工紀錄
|成員|分工內容|
|:--|:--|
|林子齊|多樣化武器系統<br>遊戲彩蛋<br>遊戲大魔王設定<br>撰寫 Appendix|
|劉承祐||
|李安||
|楊柏恩|遊戲大廳和操作設定<br>開始遊戲的流程<br>武器和爆炸音效|
|廖竑羿|PvP模式|

## 林子齊開發紀錄
2024/6/9<br>
完成:
- 拆分大的 Planewar.java
- 創建不同遊戲難度的大魔王
- 添加大魔王的音效

## 劉承祐開發紀錄

## 李安開發紀錄

## 楊柏恩開發紀錄
2024/5/31<br>
完成:
- 新增 `SoundUtil` 類
- 新增玩家子彈發射音效
- 新增敵人(小飛機)爆炸音效
- 新增 Game Over 音效
- 新增背景音樂
- 新增開始遊戲按鈕、Setting 按鈕

尚未完成:
- 按鈕美工
- 新增獲勝音效
- 新增設定功能
- 新增 Boss 爆炸音效

## 廖竑羿開發紀錄

# Appendix

## Sounds
因為音檔太大，所以放在 Google Drive 保存  
如果有需要增加的音檔直接上傳即可  
https://drive.google.com/drive/folders/1zjGFyvsrUDqzNFvgbRj6ktaB3DS74_zv?usp=sharing  
全部下載下來後創建一個名叫 sounds 的資料夾，將音檔放在裡面  

## Planewar.java  
### public class Planewar extends JFrame
#### Parameters
- width, height : 遊戲視窗的大小
- state : 紀錄目前遊戲的狀態
    - 0 : 遊戲初始化
    - 1 : 遊戲進行中
    - 3 : 遊戲失敗
    - 4 : 遊戲勝利

- score : 紀錄目前的遊戲分數
- gameLevel : 遊戲目前的關卡，會根據目前打到的 Boss 等級而有所調整
- bossAlive : 判斷 Boss 是否還存活
- backGroundMusic : 紀錄目前的背景音樂，有 Boss 時會有專屬於 Boss 的音樂
- planeObj : 玩家的戰鬥機物件
- bossObj : Boss 的物件

#### Functions

## Util
### public class SoundUtil
#### Parameters
- clip : 紀錄音頻資料

#### Functions
- `public static Clip playSound ( String soundFile, boolean loop )` : 播放音頻檔案
- `public static void stopSound ( Clip clip )` : 暫停音頻播放
