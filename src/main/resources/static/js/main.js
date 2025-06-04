// Menu
const menuBtn = document.getElementById("menu-btn");
const navLinks = document.getElementById("nav-links");

menuBtn.addEventListener("click", () => {
  navLinks.classList.toggle("open");
});

// ScrollReveal
const sr = ScrollReveal({
  origin: "top",
  distance: "100px",
  duration: 2000,
  delay: 200,
});

sr.reveal(".header__content", { origin: "bottom" });
sr.reveal(".header__image", { origin: "top" });
sr.reveal(".intro__card", { interval: 500 });
sr.reveal(".about__image", { origin: "left" });
sr.reveal(".about__content", { origin: "right" });
sr.reveal(".service__card", { interval: 500 });
sr.reveal(".client__card", { interval: 500 });
sr.reveal(".instagram__grid", { interval: 500 });

// Swiper
const swiper = new Swiper(".swiper", {
  loop: true,
  slidesPerView: "auto",
  spaceBetween: 20,
});