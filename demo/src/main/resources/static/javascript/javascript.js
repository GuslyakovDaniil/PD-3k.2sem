    function searchBooks() {
        var searchInput = document.getElementById("searchInput").value;

        // Отправка AJAX запроса на сервер для получения списка книг
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/search?query=" + searchInput, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var books = JSON.parse(xhr.responseText);
                // Обновление списка книг на странице
                updateBookList(books);
            }
        };
        xhr.send();
    }

    function updateBookList(books) {
        var bookContainer = document.getElementsByClassName("book-container")[0];
        bookContainer.innerHTML = "";
        books.forEach(function (book) {
            var bookElement = document.createElement("div");
            bookElement.classList.add("book");
            if (!book.level) {
                bookElement.classList.add("book-occupied");
            }
            var linkElement = document.createElement("a");
            linkElement.href = "/books/" + book.id;
            var titleElement = document.createElement("h2");
            titleElement.innerText = book.title;
            var authorElement = document.createElement("p");
            authorElement.innerHTML = "Автор: <span>" + book.author + "</span>";
            var genreElement = document.createElement("p");
            genreElement.innerHTML = "Жанр: <span>" + book.genre + "</span>";
            var descriptionElement = document.createElement("p");
            descriptionElement.innerHTML = "Описание: <span>" + book.description + "</span>";
            linkElement.appendChild(titleElement);
            linkElement.appendChild(authorElement);
            linkElement.appendChild(genreElement);
            linkElement.appendChild(descriptionElement);
            bookElement.appendChild(linkElement);
            bookContainer.appendChild(bookElement);
        });
    }