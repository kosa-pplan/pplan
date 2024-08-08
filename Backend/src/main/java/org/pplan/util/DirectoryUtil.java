package org.pplan.util;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * 디렉토리와 파일 관련 유틸리티 메서드를 제공하는 클래스입니다.
 */
public class DirectoryUtil {

    /**
     * 지정된 디렉토리가 존재하지 않으면 생성합니다.
     *
     * @param directoryPath 생성할 디렉토리의 경로
     */
    public static void createDirectoriesIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath); // 문자열 경로를 Path 객체로 변환합니다.
        if (Files.notExists(path)) { // 디렉토리가 존재하지 않는 경우
            try {
                Files.createDirectories(path); // 디렉토리를 생성합니다.
            } catch (IOException e) {
                throw new RuntimeException(e); // IOException이 발생하면 런타임 예외로 래핑하여 던집니다.
            }
        }
    }

    /**
     * 지정된 디렉토리의 모든 파일과 서브디렉토리를 방문하고,
     * 각 파일과 디렉토리의 경로를 출력합니다.
     *
     * @param dir 탐색할 디렉토리의 경로
     * @throws IOException 디렉토리 탐색 중 I/O 오류가 발생하면 예외를 던집니다.
     */
    public static void refreshDirectory(Path dir) throws IOException {
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            /**
             * 파일을 방문할 때 호출됩니다.
             *
             * @param file 방문한 파일의 경로
             * @param attrs 파일의 기본 속성
             * @return 파일 방문 후 계속 탐색할지 여부를 지정하는 결과
             */
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                System.out.println("파일: " + file); // 방문한 파일의 경로를 출력합니다.
                return FileVisitResult.CONTINUE; // 탐색을 계속합니다.
            }

            /**
             * 디렉토리 방문을 완료한 후 호출됩니다.
             *
             * @param dir 방문한 디렉토리의 경로
             * @param exc 디렉토리 탐색 중 발생한 예외 (있다면)
             * @return 디렉토리 방문 후 계속 탐색할지 여부를 지정하는 결과
             */
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                System.out.println("디렉터리: " + dir); // 방문한 디렉토리의 경로를 출력합니다.
                return FileVisitResult.CONTINUE; // 탐색을 계속합니다.
            }
        });
    }
}