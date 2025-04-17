document.addEventListener("DOMContentLoaded", function() {
    const stars = document.querySelectorAll('.rating span');
    const ratingInput = document.querySelector('input[name="rating"]');

    stars.forEach((star, idx) => {
        star.addEventListener('click', () => {
            const rating = star.getAttribute('data-value');
            ratingInput.value = rating;

            stars.forEach((s, i) => {
                s.classList.toggle('active', i < rating);
            });
        });
    });
});
