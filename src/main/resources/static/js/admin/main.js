function getLabels(res) {
  const keys = Object.keys(res);
}

$(() => {
  console.log("Hello")
  $.ajax("/api/stat/chart", {
    type: "GET",
    dataType: "json",
    success: (res) => {
      const chartArea = $("#chart")
      new Chart(chartArea, {
        type: 'bar',
        data: {
          label: [""]
        }
      })
    },
    error: (error) => {
      console.log(error)
    }
  })
})