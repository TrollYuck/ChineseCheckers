package pwr.tp.domain;

public class GamaData {
    public static String printBoardTypes() {
        StringBuilder output = new StringBuilder("Available board types: ");
        for(BoardTypes boardTypes: BoardTypes.values()) {
            output.append(boardTypes.toString());
            output.append(",");
        }
        output.deleteCharAt(output.length() - 1);
        return output.toString();
    }
}
