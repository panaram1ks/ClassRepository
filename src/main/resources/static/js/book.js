addBookBtnEvent()
actionsInTable()
searchAction()

document.addEventListener("DOMContentLoaded", function () {

})

function searchAction() {
    document.getElementById('searchBtn').addEventListener('click', function (event) {
        event.preventDefault()
        findBooks()
    })
}

async function findBooks() {
    const path = 'http://localhost:8081/references'
    let searchStr = document.getElementById('searchParam').value
    console.log(searchStr)
    const param = new URLSearchParams({
        "str": searchStr
    })
    fetch(path + "/filter?" + param).then(response => response.text()).then(fragment => {
        document.querySelector(".book_list").innerHTML = fragment
        addBookBtnEvent()
    })
}


function addBookBtnEvent() {
    document.getElementById("addBtnBook").addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute("href")
        fetch(href).then(response => response.text()).then(fragment => {
            document.getElementById('addModalBook').innerHTML = fragment
        }).then(() => {
            let modal = new bootstrap.Modal(document.getElementById('addModalBook'), {})
            modal.show()
            document.getElementById('add_book')
                .addEventListener('submit', event => submitNewBookForm(event))
        })
    })

}

async function submitNewBookForm(event) {
    event.preventDefault()
    let formData = new FormData(event.target)
    let request = new Request(event.target.action, {
        method: 'POST',
        body: formData,
        enctype: 'multipart/form-data'
    })
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('addModalBook'))
        modal.hide()
        document.querySelector(".book_list").innerHTML = fr
        actionsInTable()
    })
}
async function submitEditBookForm(event) {
    event.preventDefault()
    let formData = new FormData(event.target)
    let request = new Request(event.target.action, {
        method: 'POST',
        body: formData
    })
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('editModalBook'))
        modal.hide()
        document.querySelector(".book_list").innerHTML = fr
        actionsInTable()
    })
}

function deleteBook(href) {
    fetch(href).then(response => response.text()).then(fr => {
        document.querySelector(".book_list").innerHTML = fr
    })

}

function editBook(href){
    fetch(href).then(response => response.text()).then(fragment => {
        document.getElementById('editModalBook').innerHTML = fragment
    }).then( ()=>{
        let modal = new bootstrap.Modal(document.getElementById('editModalBook'))
        modal.show()
        document.getElementById('edit_book')
            .addEventListener('submit', event => submitEditBookForm(event))
    })
}


function actionsInTable() {

    document.querySelectorAll('.book_list .deleteBook').forEach(el => el.addEventListener('click', function (event) {
        event.preventDefault()
        let href = el.getAttribute('href')
        deleteBook(href)
    }))

    document.querySelectorAll('.book_list .editBook').forEach(el => el.addEventListener('click', function (event){
        event.preventDefault()
        let href = el.getAttribute('href')
        editBook(href)
    }))
}

