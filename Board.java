public class Board {
    private int _size;
    private int _numPlayers;
    private int[] _elements;
    private int _current;
    private int _winner;


    private void initializeElements() {
        _current = 0;
        _winner = 0;
        _elements = new int[_size*_size];
        for(int i = 0; i < _size*_size; i ++){
            _elements[i] = 0;
        }
    }

    private int getRow(int index){
        return index / _size;
    }

    private int getColumn(int index){
        return index % _size;
    }

    private int getIndex(int row, int column){
        return (row * _size) + column;
    }

    private boolean isInside(int row, int column){
        return ((row >= 0) && (column >= 0) && (row < _size) && (column < _size));
    }

    public int getSize() {
        return _size;
    }

    public int getNumPlayers() {
        return _numPlayers;
    }

    public int getCurrent() {
        return _current;
    }

    public int turn(int index){
        if(!(index > 0 && index <= _size*_size)){
            //Invalid input: re-enter slot number
            return -1;
        }
        if(_elements[index - 1] != 0){
            //Slot already taken: re-enter slot number
            return -2;
        }
        _elements[index - 1] = _current + 1;
        _current = (_current + 1) % _numPlayers;
        return 0;
    }

    public int getWinner() {
        if(_winner != 0){
            return _winner;
        }
        boolean[] visited = new boolean[_size*_size];
        for(int i = 0; i < _size*_size; i ++){
            visited[i] = false;
        }
        boolean foundWinner = false;
        boolean hasEmptySlot = false;
        //check for winner
        for(int i = 0; i < _size*_size; i ++){
            if(_elements[i] == 0){
                hasEmptySlot = true;
                continue;
            }
            else if(visited[i]){
                continue;
            }
            int row = getRow(i);
            int column = getColumn(i);
            int count = 0;

            //check Horizontally
            for(int ci = 0; ci < _size; ci ++){
                if(_elements[getIndex(row, ci)] != _elements[i]){
                    break;
                }
                count++;
            }
            if(count == _size){
                foundWinner = true;
                _winner = _elements[i];
                return _winner;
            }

            count = 0;
            //check vertically
            for(int ri = 0; ri < _size; ri ++){
                if(_elements[getIndex(ri, column)] != _elements[i]){
                    break;
                }
                count ++;
            }
            if(count == _size){
                foundWinner = true;
                _winner = _elements[i];
                return _winner;
            }

            //check diagonally
            if(row == column){
                //in first diagonal
                count = 0;
                for(int di = 0; di < _size; di ++){
                    if(_elements[getIndex(di, di)] != _elements[i]){
                        break;
                    }
                    count++;
                }
                if(count == _size) {
                    foundWinner = true;
                    _winner = _elements[i];
                    return _winner;
                }
            }

            if (row + column == _size - 1){
                //in second diagonal
                count = 0;
                for(int di = 0; di < _size; di ++){
                    if(_elements[getIndex(di, _size - 1 - di)] != _elements[i]){
                        break;
                    }
                    count++;
                }
                if(count == _size) {
                    foundWinner = true;
                    _winner = _elements[i];
                    return _winner;
                }
            }
        }
        if(!foundWinner && hasEmptySlot){
            //game can continue
            return 0;
        }
        //draw
        _winner = -1;
        return _winner;
    }

    public synchronized void printBoard(){
        for(int row = 0; row < _size; row ++){
            if(row != 0){
                for(int j = 0; j < 4 * _size; j ++) {
                    System.out.print("-");
                }
                System.out.print("\n");
            }
            for(int col = 0; col < _size; col ++){
                if(col != 0){
                    System.out.print("|");
                }
                if(_numPlayers == 2){
                    if(_elements[getIndex(row, col)] == 1){
                        System.out.print(" X ");
                    }else if(_elements[getIndex(row, col)] == 2){
                        System.out.print(" O ");
                    }else {
                        System.out.print("   ");
                    }
                } else {
                    System.out.print(" " +
                            ((_elements[getIndex(row, col)] == 0) ? " " : _elements[getIndex(row, col)]) +
                            " ");
                }
            }
            System.out.print("\n");
        }
    }

    //default
    Board() {
        _size = 3;
        _numPlayers = 2;
        initializeElements();
    }

    Board(int size){
        _size = size;
        _numPlayers = 2;
        initializeElements();
    }

    Board(int size, int players){
        _size = size;
        _numPlayers = players;
        initializeElements();
    }


}
