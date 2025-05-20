function getLabels(res) {
  const keys = Object.keys(res);
}

$(() => {
  const chartArea = $("#chart")
  $.ajax("/api/stat/chart", {
    type: "GET",
    dataType: "json",
    contentType: "application/json;utf-8",
    success: (res) => {
      const labels = [];
      $.each(res, function (index, item) {
        console.log(item)
      })
    },
    error: (error) => {
      console.log(error)
    }
  })
})