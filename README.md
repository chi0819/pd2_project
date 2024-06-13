# 程式設計期末專題開發紀錄

## 大綱
- 主題 : 飛機大戰
- 介紹 : 復刻飛機大戰遊戲，並且添加新功能

## 分工紀錄
|成員|分工內容|
|:--|:--|
|林子齊|多樣化武器系統<br>不同等級的遊戲魔王<br>撰寫 Appendix 和註解|
|劉承祐||
|李安||
|楊柏恩|遊戲大廳和操作設定<br>開始遊戲的流程<br>武器和爆炸音效|
|廖竑羿|PvP模式|

## 林子齊開發紀錄
2024/6/9<br>
完成:
- 創建不同遊戲難度的大魔王
- 添加大魔王的音效

2024/6/11<br>
完成:
- 添加程式碼註解
- 添加音效


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

2024/6/9<br>
完成:
- 新增 `SettingsDialog` 類
- 新增 Setting 視窗
- 增加了調整背景音樂音量大小的功能
- 增加調整視窗大小的功能
- 修復了使用JButton後無法使用空白鍵暫停的BUG
- 對按鈕美工做了進一步的優化
- 將物件圖片移到GameUtil開頭的變數宣告中，增加代碼可讀性和可維護性

尚未完成:
- 按鈕美工的優化
- 物件需隨著視窗大小做等比例的放大
- 將音樂檔案移到GameUtil開頭的變數宣告中以取代在代碼中出現絕對路徑降低可維護性

## 廖竑羿開發紀錄
2024/6/10<br>
完成:
- 新增 `PVPmode` 類，完成雙人模式畫面及功能雛形
- 新增 `PlayerPlaneObj` 類
- 修改 `ShellObj` 類之子彈出界條件
- 修復碰撞判定及扣血不正確問題
  
尚未完成：
- 撞到自身子彈會扣血問題
- 圖片方向問題
- 飛機邊界問題
- 勝負判斷及畫面

2024/6/12<br>
完成:
- 修正子彈撞到自身會扣血的bug
- 加入移動時邊界條件
- 改變 player1 圖片方向

尚未完成：
- 子彈圖片方向
- 勝負判斷及畫面

# Appendix

## Sounds
因為音檔和圖檔太大，所以放在 Google Drive 保存  
如果有需要增加的音檔和圖檔直接上傳即可  
https://drive.google.com/drive/folders/1zjGFyvsrUDqzNFvgbRj6ktaB3DS74_zv?usp=sharing  
創建一個名叫 sounds 的資料夾，將音檔放在裡面  
創建一個名叫 imgs 的資料夾，將圖檔放在裡面  

## Planewar.java  
### public class Planewar extends JFrame
#### Parameters
- width, height : 遊戲視窗的大小
- state : 使用 `enum` 紀錄目前遊戲的狀態
    - INITIAL : 遊戲初始化
    - GAMING : 遊戲進行中
    - PAUSE : 遊戲暫停
    - VICTORY : 遊戲勝利

- score : 紀錄目前的遊戲分數
- gameLevel : 遊戲目前的關卡，會根據目前打到的 Boss 等級而有所調整
- bossAlive : 判斷 Boss 是否還存活
- backGroundMusic : 紀錄目前的背景音樂，有 Boss 時會有專屬於 Boss 的音樂
- planeObj : 玩家的戰鬥機物件
- bossObj : Boss 的物件

#### Functions

## PVPmode.java  
- player1 : 鍵盤 WASD 控制方向
- player2 : 鍵盤上下左右控制方向

## Util
### public class SoundUtil
#### Parameters
- clip : 紀錄音頻資料

#### Functions
- `public static Clip playSound ( String soundFile, boolean loop )` : 播放音頻檔案
- `public static void stopSound ( Clip clip )` : 暫停音頻播放
