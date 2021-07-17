document.addEventListener("DOMContentLoaded", function () {
    addAuthorBtnEvent()
})

function addAuthorBtnEvent() {
    document.getElementById("addAuthorBtn").addEventListener('click', function (event) {
        event.preventDefault()
        let href = this.getAttribute("href")
        console.log(href)
        fetch(href).then(response => response.text()).then(fragment => {
            document.getElementById('addAuthorModal').innerHTML = fragment
        }).then(() => {
            let modal = new bootstrap.Modal(document.getElementById('addAuthorModal'), {})
            modal.show()
            // document.getElementById('add_author')
            //     .addEventListener('submit', event => submitNewAuthorForm(event))
        })
    })
}

async function submitNewAuthorForm(event) {
    event.preventDefault()
    let formData = new FormData(event.target)
    let request = new Request(event.target.action, {
        method: 'POST',
        body: formData
    })
    fetch(request).then(response => response.text()).then(fr => {
        let modal = bootstrap.Modal.getInstance(document.getElementById('addAuthorModal'))
        modal.hide()
        document.querySelector(".author_list").innerHTML = fr
        addAuthorBtnEvent()
    })
}