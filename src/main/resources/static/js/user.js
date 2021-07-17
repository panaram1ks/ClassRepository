document.addEventListener("DOMContentLoaded", function () {
    addUserBtnEvent()
    eventForPage()
})

function addUserBtnEvent() {
    document.getElementById("addBtn").addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute("href")
        fetch(href).then(response => response.text()).then(fragment => {
            document.querySelector("#addModal").innerHTML = fragment
        }).then(() => {
            let modal = new bootstrap.Modal(document.querySelector("#addModal"), {})
            modal.show()
            document.getElementById('add_user')
                .addEventListener('submit', event => submitNewUserForm(event))
        })
    })
}

function editUser(href) {
    fetch(href).then(response => response.text()).then(fragment => {
        document.querySelector("#editModal").innerHTML = fragment
    }).then(() => {
        let modal = new bootstrap.Modal(document.querySelector("#editModal"), {})
        modal.show()
        document.getElementById('edit_user')
            .addEventListener('submit', event => submitEditUserForm(event))
    })
}


/*
function eventForUserPage1(){
document.querySelectorAll('.table .editBtn')
    .forEach(editBtn => editEvent(editBtn))

document.querySelectorAll('.table tr')
    .forEach(tr => editEventRow(tr))
}

function editEvent(el){
el.addEventListener('click',function (event){
    event.preventDefault()
    let href = this.getAttribute('href')
    editAsyncFetch(href)
})
}

function editEventRow(el){
el.addEventListener('dblclick',function (event){
    event.preventDefault()
    let editBtn = el.querySelector('.editBtn')
    let href = this.getAttribute('href')
    editAsyncFetch(href)
})
}

function editAsyncFetch(href){
fetch(href).then(response => response.text()).then(fragment => {
    document.querySelector("#editModal").innerHTML = fragment})
    .then(()=>{
        let modal = new bootstrap.Modal(document.getElementById('editModal'),{})
        modal.show()
        document.getElementById('edit_user')
            .addEventListener('submit', event => submitEditUserForm(event))
    })
}
*/
function eventForPage() {

    document.querySelectorAll('.table tbody tr').forEach(
        tr => {
            tr.addEventListener('dblclick', event => {
                event.preventDefault()
                let href = tr.querySelector('a').getAttribute('href')
                editUser(href)
            })
        })

    document.querySelectorAll('.table .editBtn').forEach(editBtn => editBtn.addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute("href")
        editUser(href)
    }))

    document.querySelectorAll('.table .deleteBtn').forEach(deleteBtn => deleteBtn.addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute("href")
        document.querySelector('#deleteModal .modal-footer a')
            .setAttribute('href', href)
        let modal = new bootstrap.Modal(document.querySelector("#deleteModal"), {})
        modal.show()
        document.getElementById('delUser')
            .addEventListener('click', function (event) {
                event.preventDefault()
                fetch(href).then(response => response.text()).then(fragment => {
                    document.querySelector(".user_list").innerHTML = fragment
                    modal.hide()
                    eventForPage()
                })
            })
    }))
}


async function submitNewUserForm(event) {
    event.preventDefault()
    let formData = new FormData(event.target)

    let request = new Request(event.target.action, {// в экшене храниться урл
        method: 'POST',
        body: formData
    })
    const param = new URLSearchParams({
            "login": formData.get('login')
        }
    )
    fetch("users/checkLogin?" + param).then(response => {
            if (response.ok) {
                saveUser(request)
            } else {
                return Promise.reject(response);
            }
        }
    ).catch(error =>
        error.text()).then(errorFr => {
        document.querySelector("#addModal .custom-alert").innerHTML = errorFr
    })
}

function saveUser(request) {
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('addModal'))
        modal.hide()
        document.querySelector(".user_list").innerHTML = fr
        eventForPage()
    })
}


async function submitEditUserForm(event) {
    event.preventDefault()
    let formData = new FormData(event.target),
        request = new Request(event.target.action, {// в экшене храниться урл
            method: 'POST',
            body: formData
        })
    let response = await fetch(request);//в респонсе будет наш тайбл
    let userTable = await response.text()
    let modal = bootstrap.Modal.getInstance(document.getElementById('editModal'))
    modal.hide()
    document.querySelector(".user_list").innerHTML = userTable
    eventForPage()
}

const path = 'http://localhost:8081/users'

async function filterUser() {
    let searchVal = document.getElementById('searchWord').value
    const param = new URLSearchParams({
        "s": searchVal
    })
    fetch(path + "/filter?" + param).then(response => response.text()).then(fragment => {
        document.querySelector(".user_list").innerHTML = fragment
        eventForPage()
    })
}