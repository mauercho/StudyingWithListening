import dayjs from 'dayjs'

export function getWeekendRange() {
  
  const now = dayjs()
  const nowday = now.get('day')

  let thisStartDate:dayjs.Dayjs
  let thisEndDate:dayjs.Dayjs
  let lastStartDate:dayjs.Dayjs
  let lastEndDate:dayjs.Dayjs

  if(nowday == 0) { 
    thisStartDate = now.subtract(6,'day')
    thisEndDate = now
    lastStartDate = thisStartDate.subtract(7,'day')
    lastEndDate = thisEndDate.subtract(7,'day')
  } else {
    thisStartDate = now.subtract(nowday-1, 'day')
    thisEndDate = now.add(7-nowday,'day')
    lastStartDate = thisStartDate.subtract(7,'day')
    lastEndDate = thisEndDate.subtract(7,'day')
  }
  
  
  return {
    "thisWeek" : {
      "startDate" : thisStartDate.format('MM/DD'),
      "endDate" : thisEndDate.format('MM/DD'),
    },
    "lastWeek" : {
      "startDate" : lastStartDate.format('MM/DD'),
      "endDate" : lastEndDate.format('MM/DD'),
    }
  }
}