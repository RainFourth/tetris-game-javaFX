package tetris;

public enum GameState {
    LOGO,                   //->MAIN_MENU
    MAIN_MENU,              //->READY_TO_START      ->LOGO              ->CONTROLS            ->COPYRIGHT
    COPYRIGHT,              //->MAIN_MENU
    CONTROLS,               //->MAIN_MENU
    READY_TO_START,         //->RUNNING
    RUNNING,                //->PAUSE               ->GAME_OVER         ->READY_TO_START      ->LOGO
    PAUSE,                  //->RUNNING             ->READY_TO_START    ->LOGO
    GAME_OVER,              //->LOGO                ->READY_TO_START

}
