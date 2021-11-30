# BLE_Signal_Logger_Kotlin

## アプリ概要
学生の頃BLEビーコンの受信電波強度を用いた状態推定に関する研究を行っていた。
その時作成し使用していたBLEビーコンの受信電波強度を収集し保存するアプリ。

https://drive.google.com/file/d/1qLYbiNoJgOSUHca_cNBN5_ll5JL1Gogd/view

https://drive.google.com/file/d/1IG0UI7CSjktChjs761J7ML-f6k7LQZVE/view

UUID/Major/Minorからビーコンを指定しRSSI(受信電波強度)を任意の間隔で保存する。
複数個でも同時に収集保存を行えるアプリ。

一旦テキストファイルに書き出しを行う。
利用にはそのファイルを抜き出してエクセル等で色々やってデータを見る形となる。

## 目的
- Kotlinを使ったアプリ開発に慣れる

 - アーキテクチャを意識したアプリを作る


## アーキテクチャ
MVVM

## 使用言語
Kotlin 1.5

## 環境
Android Studio Arctic Fox

## 使用ライブラリ
- Groupie
- kotlinx-serialization
- ViewModel

作りながら追加予定
