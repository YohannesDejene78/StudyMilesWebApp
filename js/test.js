const targetUserID = 302;

fetch("https://studymileswebapp.onrender.com/incentive")
  .then(response => response.json())
  .then(data => {
    const result = data.find(item => item.userID.userID === targetUserID);
    if (result) {
      console.log("IncentivesID for userID 302 is:", result.incentivesID);
    } else {
      console.log("User not found in incentive data.");
    }
  })
  .catch(error => console.error("Error fetching incentive data:", error));
