package ru.git;

/**
 *    - Хранит статистику по файлам (количество файлов, размер в байтах, количество строк и т.д.).
 *    - Может быть использован для хранения и передачи данных между компонентами.
 */
public class FileStatistics {

    private int fileCount;
    private long totalSize;
    private int totalLines;
    private int nonEmptyLines;
    private int commentLines;

    public int getFileCount() {
        return fileCount;
    }

    public void setFileCount(int fileCount) {
        this.fileCount = fileCount;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    public int getNonEmptyLines() {
        return nonEmptyLines;
    }

    public void setNonEmptyLines(int nonEmptyLines) {
        this.nonEmptyLines = nonEmptyLines;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public void setCommentLines(int commentLines) {
        this.commentLines = commentLines;
    }

    public void incrementFileCount() {
        fileCount++;
    }

    public void incrementFileSize(long size) {
        totalSize += size;
    }

    public void incrementTotalLines() {
        totalLines++;
    }

    public void incrementNonEmptyLines() {
        nonEmptyLines++;
    }

    public void incrementCommentLines() {
        commentLines++;
    }

    public void sumCommentLines(int lines) {
        commentLines += lines;
    }


    @Override
    public String toString() {
        return "Статистика по файлам {" +
                "Количество = " + fileCount +
                ", Размер в байтах = " + totalSize +
                ", Количество строк = " + totalLines +
                ", Количество не пустых строк = " + nonEmptyLines +
                ", Количество строк с комментариями = " + commentLines +
                '}';
    }
}
