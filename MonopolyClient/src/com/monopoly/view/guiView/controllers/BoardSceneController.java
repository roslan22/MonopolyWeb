package com.monopoly.view.guiView.controllers;

import com.monopoly.view.guiView.guiEntities.GuiCell;
import com.monopoly.view.playerDescisions.PlayerBuyAssetDecision;
import com.monopoly.view.playerDescisions.PlayerBuyHouseDecision;
import com.monopoly.view.playerDescisions.PlayerResign;
import com.monopoly.view.guiView.Procedure;

import java.awt.geom.IllegalPathStateException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.stream.IntStream;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Transition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;


public class BoardSceneController implements Initializable
{

    public static final String CELL_RIGHT_FXML  = "cell_right.fxml";
    public static final String CELL_LEFT_FXML   = "cell_left.fxml";
    public static final String CELL_FXML        = "cell.fxml";
    public static final String CELL_CORNER_FXML = "cell_corner.fxml";

    public static final int INIT_MONEY              = 1500;
    public final static int LAST_ROW                = 9;
    public final static int FIRST_COLUMN            = 0;
    public final static int LAST_COLUMN             = 9;
    public final static int START_PLACE             = 0;
    public final static int MAX_BOARD_CELLS         = 36;
    public final static int DEFAULT_ANIMATION_SPEED = 700;
    public final static int FIRST_ROW               = 0;

    @FXML
    private GridPane gridPaneMain;

    @FXML
    private VBox vboxPlayers;

    @FXML
    private Pane promtPane, surprisePane, warningPane;

    @FXML
    private TextArea textAreaPromt, gameMsg, msgTextArea;

    @FXML
    private ImageView surpriseCard, warningCard, leftCube, rightCube;

    @FXML
    private Label warningText, surpriseText;

    @FXML
    private Button buttonResign;

    private List<CellController>        cellControllers     = new ArrayList<>();
    private List<Pane>                  boardCells          = new ArrayList<>();
    private Map<String, PlayerPosition> playersPlaceOnBoard = new TreeMap<>();
    private Map<String, String>         playerNamesAndIds   = new HashMap<>();
    private Map<String, Integer>        playerNameToMoney   = new HashMap<>();

    private PlayerBuyAssetDecision playerBuyAssetDecision;
    private int                    waitingForAnswerEventId;
    private int                    loops;
    private PlayerResign           playerResign;
    private Procedure              newGameStartProcedure;
    private Procedure              notToStartNewGameProcedure;

    private SequentialTransition seqTransition        = new SequentialTransition();
    private boolean              isGameOver           = false;
    private boolean              isAnimationsFinished = true;
    private int                  nextPlayerPlaceIndex = 1;
    private String currentPlayerName;

    @FXML
    private void onYesClicked()
    {
        hidePromptPane();
        if (!isGameOver)
        {
            playerBuyAssetDecision.onAnswer(waitingForAnswerEventId, true);
        }
        else //new game required
        {
            returnResignButtonToPromtPane();
            isGameOver = false;
            newGameStartProcedure.execute();
        }
    }

    @FXML
    private void onNoClicked()
    {
        hidePromptPane();
        if (!isGameOver)
        {
            playerBuyAssetDecision.onAnswer(waitingForAnswerEventId, false);
        }
        else
        {
            notToStartNewGameProcedure.execute();
        }
    }

    @FXML
    private void onResignClicked()
    {
        hidePromptPane();
        playerResign.resign();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        initBoardCells();
        promtPane.setVisible(false);
        warningText.setWrapText(true);
        surpriseText.setWrapText(true);
        msgTextArea.textProperty().addListener((ChangeListener<Object>) (observable, oldValue, newValue) -> msgTextArea
                .setScrollTop(Double.MAX_VALUE));
    }

    public void setPlayers(List<String> playerNames, int computerPlayers)
    {
        currentPlayerName = playerNames.get(0);
        hidePromptPane();
    }

    public void setHumanPlayers(List<String> playerNames) {
        addHumanPlayers(playerNames);
        createRightTopPlayersMenu();
    }

    private void createRightTopPlayersMenu()
    {
        vboxPlayers.getChildren().clear();
        for (String name : playersPlaceOnBoard.keySet())
        {
            HBox hbox = createPlayerHbox(name);
            vboxPlayers.getChildren().add(hbox);
        }
    }

    private HBox createPlayerHbox(String name)
    {
        HBox hbox = new HBox();
        hbox.getChildren().add(setPropertiesToPlayerIcon(playerNamesAndIds.get(name)));
        hbox.getChildren().add(new Label(name));
        hbox.getChildren().add(new Label(" has ₪" + playerNameToMoney.get(name)));
        return hbox;
    }

    private ImageView setPropertiesToPlayerIcon(String playerId)
    {
        ImageView playerIcon;
        playerIcon = new ImageView();
        playerIcon.setId(playerId);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);
        return playerIcon;
    }

    private void addHumanPlayers(List<String> humanPlayerNames)
    {
        humanPlayerNames.forEach((name) -> placePlayerOnBoard(name, START_PLACE));
    }

    private void addJoindedPlayer(String playerName)
    {
        placePlayerOnBoard(playerName, START_PLACE);
    }
    
    public void addComputerPlayers(int playersNumber)
    {
        IntStream.range(1, playersNumber + 1).forEach(i -> placePlayerOnBoard("Computer" + i, START_PLACE));
    }

    private void placePlayerOnBoard(String playerName, int placeIndex)
    {
        String playerID;
        playerID = "player" + nextPlayerPlaceIndex;

        playersPlaceOnBoard.put(playerName, createPlayerPosition(placeIndex, playerID));
        playerNamesAndIds.put(playerName, playerID);
        playerNameToMoney.put(playerName, INIT_MONEY);
        Node playerIcon = playersPlaceOnBoard.get(playerName).getPlayerIcon();
        boardCells.get(placeIndex).getChildren().add(playerIcon);
        nextPlayerPlaceIndex++;
    }

    private PlayerPosition createPlayerPosition(int placeIndex, String playerID)
    {
        return new PlayerPosition(placeIndex, createPlayerIcon(playerID));
    }

    private ImageView createPlayerIcon(String playerID)
    {
        ImageView playerIcon = new ImageView();
        playerIcon.setId(playerID);
        playerIcon.setFitHeight(30);
        playerIcon.setFitWidth(30);
        return playerIcon;
    }

    private void initBoardCells()
    {
        int currentCellNumber = 0;
        currentCellNumber = initBottomLineCells(currentCellNumber);
        currentCellNumber = initLeftLineCells(currentCellNumber);
        currentCellNumber = initTopLineCells(currentCellNumber);
        initRightLineCells(currentCellNumber);
    }

    private void initRightLineCells(int currentCellNumber)
    {
        for (int row = FIRST_ROW; row < LAST_ROW; row++)
        {
            currentCellNumber = addNewPane(currentCellNumber, LAST_COLUMN, row);
        }
    }

    private int initTopLineCells(int currentCellNumber)
    {
        for (int column = FIRST_COLUMN; column < LAST_COLUMN; column++)
        {
            currentCellNumber = addNewPane(currentCellNumber, column, FIRST_ROW);
        }
        return currentCellNumber;
    }

    private int initLeftLineCells(int currentCellNumber)
    {
        for (int row = LAST_ROW - 1; row > 0; row--)
        {
            currentCellNumber = addNewPane(currentCellNumber, FIRST_COLUMN, row);

        }
        return currentCellNumber;
    }

    private int initBottomLineCells(int currentCellNumber)
    {

        for (int column = LAST_COLUMN; column >= 0; column--)
        {
            currentCellNumber = addNewPane(currentCellNumber, column, LAST_ROW);
        }

        return currentCellNumber;
    }

    private int addNewPane(int currentCellNumber, int from, int to)
    {
        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);

        setCellId(currentCellNumber, flowPane);
        boardCells.add(currentCellNumber, flowPane);
        gridPaneMain.add(flowPane, from, to);

        currentCellNumber++;

        return currentCellNumber;
    }

    private void setCellId(int currentCellNumber, Pane currentPane)
    {
        if (isCornerCell(currentCellNumber))
        {
            currentPane.setId("cornerCell");
        }
        else
        {
            currentPane.setId("cell");
        }
    }

    private static boolean isCornerCell(int currentCellNumber)
    {
        return currentCellNumber == 0 || currentCellNumber == 9 || currentCellNumber == 18 || currentCellNumber == 27;
    }

    public void movePlayer(int cell, String PlayerName)
    {
        PlayerPosition playerPos = playersPlaceOnBoard.get(PlayerName);
        if (playerPos.getPlayerIcon() != null)
        {
            addPlayerIconToBoardMoves(calculateCellToMove(cell, playerPos.getCell()), playerPos.getPlayerIcon());
        }
    }

    private void addPlayerIconToBoardMoves(int cell, Node playerIcon)
    {
        PlayerPosition playerMovePosition = new PlayerPosition(cell, playerIcon);
        FadeTransition ft = createIconsFadeTransition(playerMovePosition.getPlayerIcon());
        seqTransition.getChildren().add(ft);
        ft.setOnFinished((ActionEvent actionEvent) -> {
            playerMovePosition.getPlayerIcon().setOpacity(1.0);
            removePlayerIconFromBoard(playerMovePosition.getPlayerIcon());
            cellControllers.get(playerMovePosition.getCell()).addPlayer(playerMovePosition.getPlayerIcon());
        });
    }

    private FadeTransition createIconsFadeTransition(Node playerIcon)
    {
        return createIconsFadeTransition(playerIcon, DEFAULT_ANIMATION_SPEED);
    }

    private FadeTransition createIconsFadeTransition(Node playerIcon, int animationSpeed)
    {
        FadeTransition ft = new FadeTransition(Duration.millis(animationSpeed), playerIcon);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        return ft;
    }

    private void removePlayerIconFromBoard(Node playerIcon)
    {
        cellControllers.stream().forEach(c -> c.removePlayer(playerIcon));
    }

    public void showMessage(String message)
    {
        addSeqTransition(() -> {
            String prevText = msgTextArea.getText();
            msgTextArea.setText(prevText + "\n" + message);
            msgTextArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    public void promptPlayer(String text, PlayerBuyAssetDecision playersDecision, int eventID)
    {
        addSeqTransition(() -> {
            this.playerBuyAssetDecision = playersDecision;
            textAreaPromt.setText(text);
            this.waitingForAnswerEventId = eventID;
        });

        startFadeAnimations();
    }

    private void startFadeAnimations()
    {
        seqTransition.setCycleCount(1);

        if (isAnimationsFinished)
        {
            seqTransition.play();
            isAnimationsFinished = false;
        }

        seqTransition.onFinishedProperty().set((ActionEvent actionEvent) -> {
            seqTransition = new SequentialTransition();
            System.out.println("ok goood - seq animations finished");
            isAnimationsFinished = true;
            if (!isGameOver)
            {
                showPromptPane();
            }
            else
            {
                showTryAnotherGamePane();
            }
        });
    }

    private void hidePromptPane()
    {
        promtPane.setVisible(false);
    }

    private void showPromptPane()
    {
        promtPane.setVisible(true);
    }

    public void initCellsNames(List<? extends GuiCell> drawableCells)
    {
        for (int i = 0; i < drawableCells.size(); i++)
        {
            GuiCell drawableProperty = drawableCells.get(i);
            if (i % 9 == 0)
            {
                addCellFxml(i, drawableProperty, CELL_CORNER_FXML);
            }
            else if ((0 <= i && i <= 9) || (18 <= i && i <= 27))
            {
                addCellFxml(i, drawableProperty, CELL_FXML);
            }
            else if (9 <= i && i <= 18)
            {
                addCellFxml(i, drawableProperty, CELL_LEFT_FXML);
            }
            else
            {
                addCellFxml(i, drawableProperty, CELL_RIGHT_FXML);
            }
            repaint();
        }
    }

    private void addCellFxml(int cellIndex, GuiCell drawableProperty, String cellFxml)
    {
        FXMLLoader getNamesFXMLLoader = getFXMLLoader(cellFxml);
        Parent root = getRoot(getNamesFXMLLoader);
        ((CellController) getNamesFXMLLoader.getController()).setDrawableProperty(drawableProperty);
        cellControllers.add(getNamesFXMLLoader.getController());
        boardCells.get(cellIndex).getChildren().add(root);
    }

    private void repaint()
    {
        cellControllers.forEach(CellController::paint);
    }

    private FXMLLoader getFXMLLoader(String fxmlPath)
    {
        return new FXMLLoader(getClass().getResource(fxmlPath));
    }

    private Parent getRoot(FXMLLoader fxmlLoader)
    {
        try
        {
            return (Parent) fxmlLoader.load(fxmlLoader.getLocation().openStream());
        } catch (IOException e)
        {
            e.printStackTrace();
            throw new IllegalPathStateException(e.getMessage());
        }
    }

    private int calculateCellToMove(int cell, int currentCell)
    {
        if (cell + currentCell >= MAX_BOARD_CELLS)
        {
            return (cell + currentCell - MAX_BOARD_CELLS);
        }

        return cell + currentCell;
    }

    public void updateMoney(String fromPlayerName, String toPlayerName, int paymentAmount)
    {
        addSeqTransition(() -> {
            playerNameToMoney.computeIfPresent(fromPlayerName, (k, v) -> v - paymentAmount);
            playerNameToMoney.computeIfPresent(toPlayerName, (k, v) -> v + paymentAmount);
            createRightTopPlayersMenu();
        });
    }

    public void playerLost(String eventMessage, String playerName)
    {
        addSeqTransitionToTextArea(eventMessage, gameMsg);
        addSeqTransition(() -> {
            removePlayerAssets(playerName);
            removePlayerIcon(playerName);
        });
    }

    private void removePlayerAssets(String playerName)
    {
        addSeqTransition(() -> cellControllers.forEach(c -> c.playerLost(playerName)));
    }

    public void showGameOverMsg(String eventMessage)
    {
        addSeqTransitionToTextArea(eventMessage, gameMsg);
        isGameOver = true;
        startFadeAnimations();
    }

    public void showDiceRollResult(String playerName, String eventMessage, int firstResult, int secondResult)
    {
        SequentialTransition st = new SequentialTransition();
        showThrowingCubeMessage("Player " + playerName + " throwing cubes now.");

        st.getChildren().addAll(getRandomCubeTransition(), getRandomCubeTransition(), getRandomCubeTransition());
        st.setOnFinished(e -> {
            leftCube.setImage(new Image(getClass().getResourceAsStream("boardImages/" + firstResult + ".png")));
            rightCube.setImage(new Image(getClass().getResourceAsStream("boardImages/" + secondResult + ".png")));
        });
        seqTransition.getChildren().add(st);
    }

    private RotateTransition getRandomCubeTransition()
    {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(500), leftCube);
        rotateTransition.setByAngle(0);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setOnFinished(e -> {
            leftCube.setImage(new Image(getClass().getResourceAsStream("boardImages/" + (new Random()
                    .nextInt(6) + 1) + ".png")));
            rightCube.setImage(new Image(getClass().getResourceAsStream("boardImages/" + (new Random()
                    .nextInt(6) + 1) + ".png")));
        });

        return rotateTransition;
    }


    public void showSurpriseCard(String cardText)
    {
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren()
                .addAll(getPathTransition(this.surprisePane, cardText, 97, 68, surpriseCard, surpriseText),
                        getRotateTransition(this.surprisePane));
        parallelTransition.setAutoReverse(true);
        parallelTransition.setCycleCount(2);
        seqTransition.getChildren().add(parallelTransition);
    }

    public void showWarningCard(String eventMessage)
    {
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren()
                .addAll(getPathTransition(this.warningPane, eventMessage, 97, 68, warningCard, warningText),
                        getRotateTransition(this.warningPane));
        parallelTransition.setAutoReverse(true);
        parallelTransition.setCycleCount(2);
        seqTransition.getChildren().add(parallelTransition);
    }

    private RotateTransition getRotateTransition(Node card)
    {
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2000), card);
        rotateTransition.setByAngle(360 - card.getRotate());
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        return rotateTransition;
    }

    private Transition getPathTransition(Pane card, String cardText, int initX, int initY, ImageView imageCard, Label label)
    {
        SequentialTransition st = new SequentialTransition();

        double destX = 350;
        double destY = 350;
        Path path = new Path();
        path.getElements().add(new MoveTo(initX, initY));
        path.getElements().add(new CubicCurveTo(initX,
                                                initY,
                                                (destX - card.getLayoutX()) / 2,
                                                (destY - card.getLayoutY()) / 2,
                                                destX - card.getLayoutX(),
                                                destY - card.getLayoutY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(path);
        pathTransition.setNode(card);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.setOnFinished(e -> {
            loops++;
            if (loops % 2 == 0)
            {
                return;
            }
            imageCard.setVisible(false);
            label.setStyle("-fx-background-color: white;");
            label.setText(cardText);
        });

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(2000), card);
        rotateTransition.setByAngle(0);
        rotateTransition.setCycleCount(1);
        rotateTransition.setAutoReverse(false);
        rotateTransition.setOnFinished(e -> {
            if (loops % 2 == 0)
            {
                return;
            }
            imageCard.setVisible(true);
            label.setStyle(null);
            label.setText("");
        });

        st.getChildren().addAll(pathTransition, rotateTransition);
        return st;
    }

    private void addSeqTransitionToTextArea(String eventMessage, TextArea textArea)
    {
        Label gameOverLabel = new Label(eventMessage);
        FadeTransition ft = createIconsFadeTransition(gameOverLabel);
        seqTransition.getChildren().add(ft);
        ft.setOnFinished((ActionEvent actionEvent) -> textArea.setText(eventMessage));
    }

    public void initPromtDecisions(PlayerBuyAssetDecision playerBuyAssetDecision,
                                   PlayerBuyHouseDecision playerBuyHouseDecision, PlayerResign playerResign)
    {
        this.playerBuyAssetDecision = playerBuyAssetDecision;
        this.playerResign = playerResign;
    }

    public void initAnotherGameDecisions(Procedure newGameStart, Procedure notToStartNewGame)
    {
        this.newGameStartProcedure = newGameStart;
        this.notToStartNewGameProcedure = notToStartNewGame;
    }

    private void addSeqTransition(Procedure procedure)
    {
        Label gameOverLabel = new Label();
        FadeTransition ft = createIconsFadeTransition(gameOverLabel);
        seqTransition.getChildren().add(ft);
        ft.setOnFinished(e -> procedure.execute());
    }

    public void buy(String playerName, int boardSquareID)
    {
        addSeqTransition(() -> cellControllers.get(boardSquareID).buy(playerName));
    }

    private void showThrowingCubeMessage(String string)
    {
        addSeqTransition(() -> {
            gameMsg.setText(string);
            gameMsg.setScrollTop(Double.MAX_VALUE);
        });
    }

    public void showPlayerResignMsg(String eventMessage, String playerName)
    {
        addSeqTransition(() -> {
        showMessage(eventMessage);
        removePlayerAssets(playerName);
        removePlayerIcon(playerName);
        });
    }

    private void showTryAnotherGamePane()
    {
        textAreaPromt.setText("Do you want to start another game?");
        hideResignButton();
        showPromptPane();
    }

    private void removePlayerIcon(String playerName)
    {
        PlayerPosition playerPos = playersPlaceOnBoard.get(playerName);
        Node playerIcon = playerPos.getPlayerIcon();
        removePlayerIconFromBoard(playerIcon);
    }

    private void hideResignButton()
    {
        buttonResign.setVisible(false);
    }

    private void returnResignButtonToPromtPane()
    {
        buttonResign.setVisible(true);
    }

    public void showWaitingForPlayerMessage(String string) 
    {
            gameMsg.setText(string);
            gameMsg.setScrollTop(Double.MAX_VALUE);
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }
}
