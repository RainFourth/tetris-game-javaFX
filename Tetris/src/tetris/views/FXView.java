package tetris.views;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tetris.Block;
import tetris.Command;
import tetris.GameState;
import tetris.TetrisGame;

public class FXView extends Application {
    public static FXView fx;
    public static TetrisGame game;

    private Stage primaryStage;

    private Scene mainGameScene;
    private Scene logoScene;
    private Scene mainMenuScene;
    private Scene copyrightScene;
    private Scene controlsScene;

    private GridPane gridField;
    private TextField hiScore;
    private TextField score;
    private TextField lines;
    private TextField level;
    private TextField state;

    private Image[] images = new Image[7];
    private ImageView[][] imageViewsField = new ImageView[game.getField().getWidth()][game.getField().getHeight()];
    private ImageView[][] imageViewsNextFigure = new ImageView[4][4];

    private int mainMenuFieldsPos;
    private TextField[] mainMenuFields;
    private TextField level2;


    public FXView() { fx = this; }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        String imgsPath = this.getClass().getResource("imgs").toExternalForm();
        Application.setUserAgentStylesheet(imgsPath+"/style.css");
        images[0] = (new Image(imgsPath+"/block-yellow-20x20.png"));
        images[1] = (new Image(imgsPath+"/block-violet-20x20.png"));
        images[2] = (new Image(imgsPath+"/block-green-20x20.png"));
        images[3] = (new Image(imgsPath+"/block-light-blue-20x20.png"));
        images[4] = (new Image(imgsPath+"/block-orange-20x20.png"));
        images[5] = (new Image(imgsPath+"/block-blue-20x20.png"));
        images[6] = (new Image(imgsPath+"/block-red-20x20.png"));

        initLogo();
        initMainMenu();
        initMainGameScene();
        initCopyrightMenu();
        initControlsMenu();




        /*nextLabel.setFocusTraversable(true); //проброс фокуса
        gridField.setFocusTraversable(true);
        hiScore.setFocusTraversable(true);
        score.setFocusTraversable(true);
        lines.setFocusTraversable(true);
        level.setFocusTraversable(true);
        state.setFocusTraversable(true);
        info.setFocusTraversable(true);
        rootBox.setFocusTraversable(true);
        mainGameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        game.addCommand(Command.MOVE_LEFT);
                        break;
                    case RIGHT:
                        game.addCommand(Command.MOVE_RIGHT);
                        break;
                    case DOWN:
                        game.addCommand(Command.MOVE_DOWN);
                        break;
                    case SPACE:
                        game.addCommand(Command.DROP);
                        break;
                    case UP: case Q: case A: case Z:
                        game.addCommand(Command.ROTATE_LEFT);
                        break;
                    case W: case S: case X:
                        game.addCommand(Command.ROTATE_RIGHT);
                        break;
                    case ESCAPE: case NUMPAD0:
                        game.pauseOrResume();
                        break;
                    case F11:
                        game.restart();
                        break;
                    case F12:
                        game.quit();
                        break;
                }
            }
        });*/



        primaryStage.setTitle("Tetris");
        primaryStage.setMaxHeight(480);
        primaryStage.setMaxWidth(640);
        primaryStage.setMinHeight(480);
        primaryStage.setMinWidth(640);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent event) { game.quit(); }
        });
        primaryStage.show();





        /*synchronized (game) {notifyAll();}*/
        /*fXviewController.reportViewReady();*/
    }


    private void initLogo(){
        StackPane logoPane = new StackPane();
        logoPane.getStyleClass().add("logo");
        logoScene = new Scene(logoPane, 640, 480);
    }

    private void initMainMenu(){
        EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        game.mainMenuLeft();
                        break;
                    case RIGHT:
                        game.mainMenuRight();
                        break;
                    case DOWN:
                        game.mainMenuDown();
                        break;
                    case UP:
                        game.mainMenuUp();
                        break;
                    case ESCAPE:
                        game.mainMenuToLogo();
                        break;
                    case NUMPAD0: case SPACE: case Q: case A: case Z:case W: case S: case X:
                        game.mainMenuSelect();
                        break;
                    case F12:
                        game.quit();
                        break;
                }
                event.consume();
            }
        };

        mainMenuFieldsPos = game.getMainMenu().getCurrMenuPos();
        mainMenuFields = new TextField[game.getMainMenu().getMax()+1];

        TextField start0 = new TextField();
        start0.setEditable(false);
        start0.setOnKeyPressed(eh);
        start0.getStyleClass().add("text-main-menu-0");
        mainMenuFields[0]=start0;

        TextField start1 = new TextField("START");
        start1.setEditable(false);
        start1.setOnKeyPressed(eh);
        start1.getStyleClass().add("text-main-menu-1");

        HBox row0 = new HBox(start0, start1);
        row0.getStyleClass().add("hbox-main-menu");


        TextField level0 = new TextField();
        level0.setEditable(false);
        level0.setOnKeyPressed(eh);
        level0.getStyleClass().add("text-main-menu-0");
        mainMenuFields[1]=level0;

        TextField level1 = new TextField("LEVEL");
        level1.setEditable(false);
        level1.setOnKeyPressed(eh);
        level1.getStyleClass().add("text-main-menu-1");

        level2 = new TextField();
        level2.setEditable(false);
        level2.setOnKeyPressed(eh);
        level2.getStyleClass().add("text-main-menu-0");

        HBox row1 = new HBox(level0, level1, level2);
        row1.getStyleClass().add("hbox-main-menu");


        TextField copyright0 = new TextField();
        copyright0.setEditable(false);
        copyright0.setOnKeyPressed(eh);
        copyright0.getStyleClass().add("text-main-menu-0");
        mainMenuFields[2]=copyright0;

        TextField copyright1 = new TextField("COPYRIGHT");
        copyright1.setEditable(false);
        copyright1.setOnKeyPressed(eh);
        copyright1.getStyleClass().add("text-main-menu-1-1");

        HBox row2 = new HBox(copyright0, copyright1);
        row2.getStyleClass().add("hbox-main-menu");


        TextField controls0 = new TextField();
        controls0.setEditable(false);
        controls0.setOnKeyPressed(eh);
        controls0.getStyleClass().add("text-main-menu-0");
        mainMenuFields[3]=controls0;

        TextField controls1 = new TextField("CONTROLS");
        controls1.setEditable(false);
        controls1.setOnKeyPressed(eh);
        controls1.getStyleClass().add("text-main-menu-1-1");

        HBox row3 = new HBox(controls0, controls1);
        row3.getStyleClass().add("hbox-main-menu");


        VBox mainMenuBox = new VBox(row0, row1, row2, row3);
        mainMenuBox.getStyleClass().add("main-menu-box");
        mainMenuBox.setOnKeyPressed(eh);
        mainMenuScene = new Scene(mainMenuBox, 640, 480);
    }

    private void initCopyrightMenu(){
        EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case ESCAPE:case NUMPAD0: case SPACE: case Q:case A:case Z:case W:case S:case X:
                        game.toMainMenu();
                        break;
                    case F12:
                        game.quit();
                        break;
                }
                event.consume();
            }
        };

        TextArea copyright = new TextArea(
                "Фон игры:\n" +
                        "http://getyourimage.club/resize-december.html\n" +
                        "\n" +
                        "Картинки блоков:\n" +
                        "https://www.behance.net/gallery/8501735/The-Game-of-Life-Final-Major-Project"
        );
        copyright.setEditable(false);
        copyright.setOnKeyPressed(eh);
        copyright.getStyleClass().add("text-copyright");

        VBox box = new VBox(copyright);
        box.getStyleClass().add("main-menu-box");
        box.setOnKeyPressed(eh);
        copyrightScene = new Scene(box, 640, 480);
    }

    private void initControlsMenu(){
        EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case ESCAPE:case NUMPAD0: case SPACE: case Q:case A:case Z:case W:case S:case X:
                        game.toMainMenu();
                        break;
                    case F12:
                        game.quit();
                        break;
                }
                event.consume();
            }
        };

        TextArea copyright = new TextArea(
                "NUMPAD0  ESC: start/pause\n" +
                        "UP  Q  A  Z: rotate left\n" +
                        "W  S  X: rotate right\n" +
                        "SPACE: drop\n" +
                        "DOWN: move down\n" +
                        "LEFT: move left\n" +
                        "RIGHT: move right\n" +
                        "F9: reset\n" +
                        "F11: restart\n" +
                        "F12: quit"
        );
        copyright.setEditable(false);
        copyright.setOnKeyPressed(eh);
        copyright.getStyleClass().add("text-copyright");

        VBox box = new VBox(copyright);
        box.getStyleClass().add("main-menu-box");
        box.setOnKeyPressed(eh);
        controlsScene = new Scene(box, 640, 480);
    }

    private void initMainGameScene(){
        EventHandler<KeyEvent> eh = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()){
                    case LEFT:
                        game.addCommand(Command.MOVE_LEFT);
                        break;
                    case RIGHT:
                        game.addCommand(Command.MOVE_RIGHT);
                        break;
                    case DOWN:
                        game.addCommand(Command.MOVE_DOWN);
                        break;
                    case SPACE:
                        game.addCommand(Command.DROP);
                        break;
                    case UP: case Q: case A: case Z:
                        game.addCommand(Command.ROTATE_LEFT);
                        break;
                    case W: case S: case X:
                        game.addCommand(Command.ROTATE_RIGHT);
                        break;
                    case ESCAPE: case NUMPAD0:
                        game.pauseOrResume();
                        break;
                        case F9:
                        game.gameToLogo();
                        break;
                    case F11:
                        game.restart();
                        break;
                    case F12:
                        game.quit();
                        break;
                }
                event.consume();
            }
        };

        gridField = new GridPane(); gridField.setOnKeyPressed(eh);
        gridField.getStyleClass().add("grid-field");
        {
            ColumnConstraints colc = new ColumnConstraints();
            colc.setMaxWidth(20);
            colc.setMinWidth(20);
            for (int i = 0; i < game.getField().getWidth(); i++) {
                gridField.addColumn(i);
                gridField.getColumnConstraints().add(colc);
            }
            RowConstraints rowc = new RowConstraints();
            rowc.setMaxHeight(20);
            rowc.setMinHeight(20);
            for (int i = 0; i < game.getField().getHeight(); i++) {
                gridField.addRow(i);
                gridField.getRowConstraints().add(rowc);
            }

            for (int x = 0; x < game.getField().getWidth(); x++) {
                for (int y = 0; y < game.getField().getHeight(); y++) {
                    gridField.add((imageViewsField[x][game.getField().getHeight() - 1 - y] = new ImageView()), x, y);
                }
            }
        }



        TextField nextLabel = new TextField("NEXT:"); nextLabel.setOnKeyPressed(eh);
        nextLabel.getStyleClass().add("text");
        nextLabel.setEditable(false);

        GridPane nextFigure = new GridPane(); nextFigure.setOnKeyPressed(eh);
        nextFigure.getStyleClass().add("next-figure");
        {
            ColumnConstraints colc = new ColumnConstraints();
            colc.setMaxWidth(20);
            colc.setMinWidth(20);
            for (int i = 0; i < 4; i++) {
                nextFigure.addColumn(i);
                nextFigure.getColumnConstraints().add(colc);
            }
            RowConstraints rowc = new RowConstraints();
            rowc.setMaxHeight(20);
            rowc.setMinHeight(20);
            for (int i = 0; i < 4; i++) {
                nextFigure.addRow(i);
                nextFigure.getRowConstraints().add(rowc);
            }

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    nextFigure.add((imageViewsNextFigure[x][4 - 1 - y] = new ImageView()), x, y);
                }
            }
        }


        hiScore = new TextField("HI-SCORE:"); hiScore.setOnKeyPressed(eh);
        hiScore.getStyleClass().add("text");
        hiScore.setEditable(false);

        score = new TextField("SCORE:");score.setOnKeyPressed(eh);
        score.getStyleClass().add("text");
        score.setEditable(false);

        lines = new TextField("LINES:");lines.setOnKeyPressed(eh);
        lines.getStyleClass().add("text");
        lines.setEditable(false);

        level = new TextField("LEVEL:");level.setOnKeyPressed(eh);
        level.getStyleClass().add("text");
        level.setEditable(false);

        state = new TextField();state.setOnKeyPressed(eh);
        state.getStyleClass().add("text");
        state.setEditable(false);


        VBox info = new VBox(nextLabel, nextFigure, hiScore, score, lines, level, state);info.setOnKeyPressed(eh);
        info.getStyleClass().add("info");

        HBox rootBox = new HBox(gridField, info);rootBox.setOnKeyPressed(eh);
        rootBox.getStyleClass().add("root-box");

        mainGameScene = new Scene(rootBox, 640, 480);
    }



    private void setLogoScene(){ primaryStage.setScene(logoScene); }
    private void setGameMainScene(){ primaryStage.setScene(mainGameScene); }
    private void setCopyrightScene(){ primaryStage.setScene(copyrightScene); }
    private void setControlsScene(){ primaryStage.setScene(controlsScene); }
    private void setMainMenuScene(){ primaryStage.setScene(mainMenuScene); }



    private Block[][] emptyBlk = new Block[game.getField().getWidth()][game.getField().getHeight()];
    private Block[][] emptyBlk4 = new Block[4][4];


    public void refresh(){
        switch (game.getGameState()){
            case LOGO: if (primaryStage.getScene()!=logoScene) {setLogoScene();} break;

            case MAIN_MENU: if (primaryStage.getScene()!=mainMenuScene) setMainMenuScene();
                level2.setText(game.getMainMenu().getStartLvl()+"");
                mainMenuFields[mainMenuFieldsPos].clear();
                mainMenuFieldsPos=game.getMainMenu().getCurrMenuPos();
                mainMenuFields[mainMenuFieldsPos].setText("▶");
                break;

            case COPYRIGHT: if (primaryStage.getScene()!=copyrightScene) {setCopyrightScene();} break;

            case CONTROLS: if (primaryStage.getScene()!=controlsScene) {setControlsScene();} break;

            case GAME_OVER: case PAUSE: case RUNNING: if (primaryStage.getScene()!=mainGameScene) setGameMainScene();

                Block[][] b = game.getGameState() == GameState.PAUSE ? emptyBlk : game.getField().getBlocks();

                for (int x = 0; x < b.length; x++) {
                    for (int y = 0; y < b[0].length; y++) {
                        if (b[x][y] != null) imageViewsField[x][y].setImage(images[b[x][y].getColor()]);
                        else imageViewsField[x][y].setImage(null);

                    }
                }

                b = game.getField().getNextFigure()==null ? emptyBlk4 : game.getField().getNextFigure().getBlocks();

                for (int x = 0; x < 4; x++) {
                    for (int y = 0; y < 4; y++) {
                        if (x<b.length && y<b[0].length && b[x][y] != null) imageViewsNextFigure[x][y].setImage(images[b[x][y].getColor()]);
                        else imageViewsNextFigure[x][y].setImage(null);
                    }
                }

                hiScore.setText("HI-SCORE:  "+game.getField().getHiScore());
                score.setText("SCORE:  "+game.getField().getScore());
                lines.setText("LINES:  "+game.getField().getLines());
                level.setText("LEVEL:  "+(game.getField().getCurrLevel() == game.getField().getMaxLevel() ?
                        game.getField().getCurrLevel()+"  [max]" : game.getField().getCurrLevel()) );

                if (game.getGameState()==GameState.PAUSE) state.setText("PAUSE");
                else if (game.getGameState()==GameState.GAME_OVER) state.setText("GAME OVER");
                else state.setText("");

                break;

        }
    }


}

/*switch (game.getGameState()) {
            case WAITING_FOR_START: state.setText("WAITING FOR START");break;
            case RUNNING: state.setText("RUNNING...");break;
            case PAUSE: state.setText("PAUSE");break;
            case GAME_OVER: state.setText("GAME OVER");break;
        }*/


/*try {
 *//*File file = new File(URLDecoder.decode(SwingView.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath(), "UTF-8").substring(1));

            while (!file.getName().equals("TetrisGame")) file = file.getParentFile();

            String pathToBlocks = "\\Tetris\\src\\tetris\\views\\imgs\\";

            URL url = this.getClass().getResource("imgs\\style.css");
            Application.setUserAgentStylesheet(url.toExternalForm());

            URL url2 = this.getClass().getResource("imgs");



            images[0] = (new Image(new FileInputStream(url2.toExternalForm()+"\\block-yellow-20x20.png")));
            images[1] = (new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-violet-20x20.png")));
            images[2] = (new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-green-20x20.png")));
            images[3] = (new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-light-blue-20x20.png")));
            images[4] = (new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-orange-20x20.png")));
            images[5] = (new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-blue-20x20.png")));
            images[6] = (new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-red-20x20.png")));*//*



        }
        catch (Exception e) { e.printStackTrace(); }*/

        /*imgs[0] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-yellow-20x20.png")));
            imgs[1] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-violet-20x20.png")));
            imgs[2] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-green-20x20.png")));
            imgs[3] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-light-blue-20x20.png")));
            imgs[4] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-orange-20x20.png")));
            imgs[5] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-blue-20x20.png")));
            imgs[6] = new ImageView(new Image(new FileInputStream(file.getPath()+pathToBlocks+"block-red-20x20.png")));*/

/*gridField.getStyleClass().clear();*/

        /*gridField.setStyle(
                "-fx-border-color: gray;" +
                        "-fx-border-style: solid;" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 2px;" *//*+
                        "-fx-grid-lines-visible: true"*//*
        );*/


        /*space = new Pane();
        space.setStyle(
                "fx-min-width: 20px;" +
                        "fx-max-width: 20px;" +
                        "fx-min-height: 20px;" +
                        "fx-max-height: 20px;"
        );*/



        /*gridField.add(imgs[0], 0, 0);
        grid.add(imgs[1], 1, 1);
        grid.add(imgs[2], 3, 19);
            grid.add(space, 4, 18);
            grid.add(imgs[3], 9, 20);*/

/*grid.getColumnConstraints().

        for (Node node: grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            if (rowIndex != null) {
                RowConstraints rowConstraints = gp.getRowConstraints().get(rowIndex);

                if (node.getBoundsInLocal().getHeight() > rowConstraints.getPrefHeight()) {
                    node.setClip(new Rectangle(100, rowConstraints.getMaxHeight()));
                    GridPane.setVgrow(node, Priority.SOMETIMES);
                }
            }
        }*/

        /*TableView<Image> fieldTable = new TableView<>();
        fieldTable.setStyle("" +
                "-fx-background-color: orange;" +
                "-fx-min-width: 100px;" +
                "-fx-max-width: 100px;" +
                "");*/

/*info.setStyle(
 *//*"-fx-padding: 20px;" +*//*
                "-fx-spacing: 10px;" *//*+*//*
 *//*"-fx-fill-height: false;"*//*
        );*/



/*root.getStyleClass().clear();*/

        /*root.setBackground(new Background(new BackgroundImage(
                bgc, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT
        )));*/
        /*root.setStyle(
                "-fx-padding: 20px;" +
                        "-fx-spacing: 10px;" +
                        "-fx-fill-height: false;" +
                        "-fx-background-image: url(\"imgs\\bgc.jpg\");" +
                        "-fx-background-position: center;" +
                        "-fx-background-repeat: no-repeat | no-repeat;" +
                        "-fx-background-size: cover;"

        );*/



        /*if (url == null) {
            System.out.println("Resource not found. Aborting.");
            System.exit(-1);
        }*/
/*root.getStylesheets().add(url.toExternalForm());*/






        /*GridPane mainMenuGrid = new GridPane();
        mainMenuGrid.getStyleClass().add("grid-main-menu");*/
/*{
 *//*ColumnConstraints colc = new ColumnConstraints();
            colc.setMaxWidth(20);
            colc.setMinWidth(20);*//*
            for (int i = 0; i < 4; i++) {
                nextFigure.addColumn(i);
                nextFigure.getColumnConstraints().add(colc);
            }
            RowConstraints rowc = new RowConstraints();
            rowc.setMaxHeight(20);
            rowc.setMinHeight(20);
            for (int i = 0; i < 4; i++) {
                nextFigure.addRow(i);
                nextFigure.getRowConstraints().add(rowc);
            }

            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    nextFigure.add((imageViewsNextFigure[x][4 - 1 - y] = new ImageView()), x, y);
                }
            }
        }



        /*mainMenuGrid.add(start0, 0, 0);
        mainMenuGrid.add(start1, 1, 0);

        mainMenuGrid.add(level0, 0, 1);
        mainMenuGrid.add(level1, 1, 1);
        mainMenuGrid.add(level2, 2, 1);

        mainMenuGrid.add(copyright0, 0, 2);
        mainMenuGrid.add(copyright1, 1, 2);

        ▶
        */