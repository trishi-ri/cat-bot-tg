package ru.v1as.tg.cat.callbacks.phase.curios_cat;

import static ru.v1as.tg.cat.callbacks.is_cat.CatRequestVote.CAT1;
import static ru.v1as.tg.cat.callbacks.is_cat.CatRequestVote.CAT2;
import static ru.v1as.tg.cat.callbacks.is_cat.CatRequestVote.NOT_CAT;

import org.springframework.stereotype.Component;
import ru.v1as.tg.cat.callbacks.phase.poll.ChooseContext;

/*
 * Author: zun_ka
 * */
@Component
public class DogAndMissCatPhase extends AbstractCuriosCatPhase {

    @Override
    protected void open() {
        messages(
                "На улице тепло и солнечно. Птички поют свои весенние песни.",
                "Вы вышли на прогулку и наслаждаетесь прекрасной погодой.",
                "Вдруг откуда-то сбоку выскакивает Любопытный Кот и, перебежав вам дорогу,"
                    + " решительно несётся к жилому дому");
        poll("Ваши действия?")
                .choice("Кот!", this::yellCat)
                .choice("Пойти за котом", this::follow)
                .send();
    }

    private void yellCat(ChooseContext chooseContext) {
        messages(
                "Вы уже открыли рот, чтобы закричать «Кот!», но поняли,"
                    + " что шустрый Любопытный Кот уже куда-то исчез.");
        catchUpCatAndClose(NOT_CAT);
    }

    private void follow(ChooseContext chooseContext) {
        messages(
                "Вы устремились за котом.",
                "Пробежав вдоль дома, Любопытный кот остановился у последнего подъезда и,"
                    + " вздыбив шерсть, воинственно оскалился.",
                "Проследив направление его взгляда, вы замечаете возле подъездной двери рычащего дворового пса,"
                    + " а за ним – небольшую испуганную кошечку, зажатую в угол.");
        poll("Что будем делать?")
                .choice("Наблюдать", this::look)
                .choice("Отвлекать пса", this::distract)
                .send();
    }

    private void distract(ChooseContext chooseContext) {
        messages(
            "Взглянув на напряжённого и взволнованного Любопытного кота,"
                + " вы решаете помочь ему спасти даму сердца.",
            "Надо как-то отвлечь пса, но как?",
            "Вы оглядываетесь вокруг в поисках чего-то, что могло бы помочь.",
            "На земле неподалеку от вас вы замечаете потерянный кем-то небольшой резиновый мячик"
                + " и несколько толстых веток.");
        poll("Что же выбрать?").choice("Мячик", this::ball).choice("Ветку", this::stick).send();

    }

    private void look(ChooseContext chooseContext) {
        messages(
            "Вы решили не вмешиваться в сложные отношения дворовых животных и понаблюдать.",
            "Любопытный Кот, искоса взглянув на вас, подбирается и, издав боевой мяв,"
                + " резко бросается на пса и вцепляется когтями в холку.",
            "Пёс от неожиданности подпрыгивает, сбрасывая с себя Любопытного Кота, и испуганно убегает.",
            "Любопытный кот аккуратно приземляется на все четыре лапы, подбегает к кошечке,"
                + " и вдвоем они скрываются за углом.",
            "Вы стали свидетелем захватывающего зрелища. Жаль, что увлекшись происходящим,"
                + " вы совсем забыли посчитать кошек.");
        catchUpCatAndClose(NOT_CAT);
    }

    private void stick(ChooseContext chooseContext) {
        messages(
                "Вы поднимаете с земли толстую ветку.",
                "Посвистев, вы привлекаете внимание пса. Тот, увидев у вас в руках палку, забывает обо всём и трусит к вам. Похоже, вы случайно выбрали его любимую игру!",
                "Вы бросаете палку далеко вбок, и пёс, восторженно вывалив язык, убегает за ней и, заигравшись, забывает и о вас, и о кошках.",
                "Любопытный кот устремляется к кошечке, вдвоем они пробегают мимо вас, благодарно мурлыкнув, и уходят по своим кошачьим делам.");
        catchUpCatAndClose(CAT2);
    }

    private void ball(ChooseContext chooseContext) {
        messages(
                "Недолго думая, вы хватаете мячик.",
                "Сжав его, вы обнаруживаете, что это мячик с пищалкой! Пёс тоже услышал этот звук и удивлённо обернулся.",
                "Вы призывно машете мячиком и бросаете его далеко в сторону.",
                "Удивительно, но пёс и не подумал за ним бежать.",
                "Однако, он отвлёкся, и этого времени хватило Любопытному коту для того, чтобы подбежать к кошечке и увести ее подальше от опасности.",
                "Прежде чем скрыться за углом вместе с подругой, Любопытный кот оборачивается и благодарно кивает вам на прощание.",
                "Пёс же, не понимая, что произошло, разочарованно уходит в другую сторону.");
        catchUpCatAndClose(CAT1);
    }
}