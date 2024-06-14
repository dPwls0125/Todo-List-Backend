package com.oop.todo.Domain.phrase;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class phraseService {

//    private final phraseRepository phraseRepository;
    public static Random random = new Random();

    private static String[] phrases = {
            "올바로 동작하지 않더라도 걱정말아라. 모든 것이 그랬다면, 넌 직업을 잃었을테니까. _ Mosher’s Law of Software Engineering",
            "우리 컴퓨터 프로그램의 안타까운 현 상태를 논한다면, 소프트웨어 개발은 여전히 흑마술이 분명하며,  아직 공학이라고 부를 수 없다. _ Bill Clinton",
            "유일한 진실을 말하자면, 객체 지향 판 ‘스파게티 코드’는, 당연히, ‘라쟈냐 코드’이다. (과하게 많은 계층) _로버트 월트만(Roberto Waltman)",
            "포트란은 꽃이 아니라 잡초다. 억세고, 이따금 개화하며, 모든 컴퓨터에서 자란다. _앨런 펄리스(Alan Perlis)",
            "내 자기중심적 의견이지만, 대부분 사람들이 작성한 C 프로그램은 6 피트 정도 밑으로 들여쓰기 한 후에 흙으로 덮어야 한다. _ Blair P. Houghton",
            "좋은 디자인은 그 때문에 소모되는 비용보다 빠르게 가치가 쌓인다._ 토마스 C. 게일(Thomas C. Gale)",
            "좋은 소프트웨어는 사용자가 원하는 것을 제공하는 것이다. _ Thomas F. O’Connell",
            "완벽함은 아무것도 더할 것이 없을 때가 아닌, 아무것도 제거할 것이 남지 않았을 때 달성된다. _Antoine de Saint-Exupéry",
            "C는 유별나고, 결함 있고,  엄청나게 성공했다. _ Dennis M. Ritchie",
            "프로그래밍은 자기 얼굴을 차는 것과 같아서, 조만간 코피가 날 것이다. _ 카일 우드버리(Kyle Woodbury)",
            "최고의 프로그래머란 조금 낫다기 보다는 그냥 좋은 존재이다. 그들은 개념 창의성, 속도, 설계의 독창성, 문제 해결 능력 중 어떤 기준으로 측정하든 10배나 낫다. _ Randall E. Stross",
            "맥도날드가 소프트웨어 회사처럼 경영되었다면, 빅맥 100개 중 하나 꼴로 식중독이 걸리고도 “미안합니다. 두 개짜리 쿠폰을 받으세요”라는 대답을 들게 될 것이다._ Mark Minasi",
            "위 코드의 버그를 조심하라. 올바르다고 증명하기만 하고 실행해 보지는 않았다._ Donald Knuth",
            "소프트웨어 개발은 무엇보다도 미술이다. _ Donald Knuth",
            "월요일에 작성한 코드를 디버깅하느라 그 주의 나머지를 허비하느니 월요일에 침대 안에 머무는게 나을 때도 있다._크리스토퍼 톰프슨",
            "코드 줄 수로 프로그래밍 진척을 측정하는 것은 무게로 비행기 제작 진척도를 측정하는 것과 같다._빌 게이츠",
            "애초에 디버깅은 코드를 작성하는 것 보다 배나 힘들다. 그러니, 코드를 최대한 빈틈없이 작성하는 사람은, 당연히, 디버그할 정도로 똑똑하지 않은 것이다._Brian W. Kernighan",
            "인간적인 반복, 성스러운 재귀_ L. Peter Deutsch",
            "결국 당신 코드를 유지보수하게 될 친구가 당신이 어디에 사는지 아는 광폭한 싸이코패스가 될 것이라고 여기고 코드를 작성하라. _Martin Golding",
            "좋은 프로그래머 대부분은 돈이나 대중에게 받을 찬사를 기대하고 프로그래밍을 하지 않고 프로그래밍이 재미 있어서 한다. _ Linus Torvalds",
    };


    public String getPhrase(String userId){
        return getPhase();
    }
    private String getPhase(){
        int index = random.nextInt(0,phrases.length);
        return phrases[index];
    }
}
