# 程式設計期末專題開發紀錄

## 大綱
- 主題 : 飛機大戰
- 介紹 : 復刻飛機大戰遊戲，並且添加新功能

## 分工紀錄
|成員|分工內容|
|:--|:--|
|林子齊|不同等級的遊戲魔王<br>遊戲音效（爆炸和魔王背景音效等）<br>撰寫 Appendix 和註解<br>教同學用 Git 和 GitHub<br>Debug PR and merge<br>調整 code style|
|劉承祐|遊戲設定實作<br>retry跟home鍵實作<br>參數彈性化|
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
- 添加音效 (飛機爆炸和不同遊戲情景的音效)

2024/6/14<br>
完成:
- 每個 branch 的 code review 和 final merge debug
- 撰寫 Appendix

2024/6/18<br>
完成:
- Add LICENSE
- Remove redundant import ( bad habit about import * )
- Modified code style

## 劉承祐開發紀錄
2024/6/9<br>
完成:
- retry實作

2024/6/11<br>
完成:
- 參數彈性化、提升可讀性
- 舊版潛在bug修正

2024/6/12<br>
完成:
- 設定實作(音量、難度、視窗大小)
- 新增測試音檔
- 新增函式調控音量

2024/6/13<br>
完成:
- home實作

2024/6/14<br>
完成:
- 設定與當前狀況同步
- Clips bug 修正

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

2024/6/14<br>
完成:
- 修正子彈圖片方向
- 加入勝負判斷及畫面
- 與其他版本結合

# Appendix

## Installation
Clone the project from remote  
```
$ git clone https://github.com/chi0819/pd2_project.git
```
Enter the project  
```
$ cd pd2_project
```
Install imgs and sounds source files from google drive  
The URL is in the below Sounds&Images section  
Create two directory to store images and sounds  
```
$ mkdir imgs
$ mkdir sounds
```
### PVE Mode
PVE mode is `Planewar.java`  
Support : **Retry**, **Go back to home** and **gaming setting**  
**How to play ?** : use the mouse to control the player's plane  

Compilation  
```
$ javac Planewar.java
```
Run  
```
$ java Planewar
```
### PVP Mode
PVP mode is `PVPmode.java`  
**How to play ?** : player1 use *WASD* to control, player2 use arrow to control  

Compilation  
```
$ javac PVPmode.java
```
Run  
```
$ java PVPmode
```

## Sounds&Images
因為音檔和圖檔太大，所以放在 Google Drive 保存  
如果有需要增加的音檔和圖檔直接上傳即可  
https://drive.google.com/drive/folders/1zjGFyvsrUDqzNFvgbRj6ktaB3DS74_zv?usp=sharing  
創建一個名叫 sounds 的資料夾，將音檔放在裡面  
創建一個名叫 imgs 的資料夾，將圖檔放在裡面  
