package kr.gdj90.ex02_maven;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration //spring 환경 설정 기능의 프로그램
@ComponentScan(basePackages = {"kr.gdj90.ex02_maven"})
public class AppCtx {

}
