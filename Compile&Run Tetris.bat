chcp 65001
cd /d %~dp0
robocopy Tetris\src bin *.css *.jpg *.png /s
"C:\Program Files\Java\jdk1.8.0_202\bin\javac" -J-Dfile.encoding=utf-8 -encoding utf-8 -d bin -sourcepath Tetris/src Tetris/src/tetris/RunTetris.java
"C:\Program Files\Java\jdk1.8.0_202\bin\java" -cp bin tetris.RunTetris