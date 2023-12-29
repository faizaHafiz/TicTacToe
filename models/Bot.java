package tictactoe.models;

import tictactoe.strategies.BotPlaying.BotPlayingStrategy;
import tictactoe.strategies.BotPlaying.BotPlayingStrategyFactory;
import tictactoe.strategies.BotPlaying.EasyBotPlayingStrategy;

public class Bot extends Player{
    private BotDifficulty botDifficulty;

    public BotDifficulty getBotDifficulty() {
        return botDifficulty;
    }

    public void setBotDifficulty(BotDifficulty botDifficulty) {
        this.botDifficulty = botDifficulty;
    }

    public BotPlayingStrategy getBotPlayingStrategy() {
        return botPlayingStrategy;
    }

    public void setBotPlayingStrategy(BotPlayingStrategy botPlayingStrategy) {
        this.botPlayingStrategy = botPlayingStrategy;
    }

    private BotPlayingStrategy botPlayingStrategy;
    public Bot(Long id,String name, Symbol symbol,BotDifficulty botDifficulty){
        super(id,name,symbol,PlayerType.BOT);
        this.botDifficulty = botDifficulty;
        this.botPlayingStrategy = BotPlayingStrategyFactory
                .getBotPlayingStrategyForDifficultyLevel(botDifficulty);
    }

    @Override
    public Move makeMove(Board board) {
        Move botMove = botPlayingStrategy.makeMove(board);
        botMove.setPlayer(this);

        return botMove;
    }
}
