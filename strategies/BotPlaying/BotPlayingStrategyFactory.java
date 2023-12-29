package tictactoe.strategies.BotPlaying;

import tictactoe.models.BotDifficulty;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategyForDifficultyLevel(
            BotDifficulty botDifficulty
    ){
        return new EasyBotPlayingStrategy();
    }
}
