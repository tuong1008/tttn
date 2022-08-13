const btnNext = document.getElementById('btn__slide--next');
const slideShowNext = function () {
    let slideList = document.getElementsByClassName('slide__item');
    let n = slideList.length;
    for (let i = 0; i < n; i++) {
        if (slideList[i].classList.contains('slide__item--active')) {
            slideList[i].classList.remove('slide__item--active');
            let idx = (i === n - 1) ? 0 : i + 1;
            slideList[idx].classList.add('slide__item--active');
            break;
        }
    }
};

btnNext.onclick = function () {
    slideShowNext();
}

let btnPrev = document.getElementById('btn__slide--prev');

btnPrev.onclick = function () {
    let slideList = document.getElementsByClassName('slide__item');
    let n = slideList.length;
    for (let i = 0; i < n; i++) {
        if (slideList[i].classList.contains('slide__item--active')) {
            slideList[i].classList.remove('slide__item--active');
            let idx = (i === 0) ? n - 1 : i - 1;
            slideList[idx].classList.add('slide__item--active');
            break;
        }
    }
}
setInterval(function () {
    slideShowNext();
}, 2000);

