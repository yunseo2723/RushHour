package Controller.Frame;
public class Record implements Comparable<Record> {
    private String nickname;
    private int moveCount;
    private int elapsedTime;

    public Record(String nickname, int moveCount, int elapsedTime) {
        this.nickname = nickname;
        this.moveCount = moveCount;
        this.elapsedTime = elapsedTime;
    }

    public String getNickname() {
        return nickname;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }
    
    @Override
    public String toString() {
        return nickname + "," + moveCount + "," + elapsedTime;
    }
    
    @Override
    public int compareTo(Record other) {
        // �̵� Ƚ���� ���� ������ ���ĵǵ��� ��
        if (this.moveCount < other.moveCount) {
            return -1;
        } else if (this.moveCount > other.moveCount) {
            return 1;
        } else {
            // �̵� Ƚ���� ���� ��� �ɸ� �ð��� ���� ������ ���ĵǵ��� ��
            if (this.elapsedTime < other.elapsedTime) {
                return -1;
            } else if (this.elapsedTime > other.elapsedTime) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}