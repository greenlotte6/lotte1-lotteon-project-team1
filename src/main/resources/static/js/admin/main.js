function getLabels(res) {
  const keys = Object.keys(res);
}

$(() => {
  const chartArea = $("#chart")
  $.ajax("/api/stat/chart", {
    type: "GET",
    dataType: "json",
    success: (res) => {
      console.log(res)
      const data = res["barData"]
      const labels = [];
      for (let d of data) {
        labels.push(d["date"])
      }
      console.log(labels)
    },
    error: (error) => {
      console.log(error)
    }
  })
})