'use client'

import {
  Chart as ChartJS,
  ChartData,
  ChartOptions,
  CategoryScale,
  LinearScale,
  LineController,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js'
import { Line } from 'react-chartjs-2'
import { WeekRange } from '@/app/report/_components/ReportFieldLayout'
import { StudyInfoProps } from '@/app/report/_components/ReportFieldStudyInfo'

ChartJS.register(
  CategoryScale,
  LinearScale,
  LineController,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
)

export interface WeekData {
  mon: StudyInfoProps
  tue: StudyInfoProps
  wed: StudyInfoProps
  thu: StudyInfoProps
  fri: StudyInfoProps
  sat: StudyInfoProps
  sun: StudyInfoProps
}

interface WeekRangeProps {
  thisWeek: WeekRange
  lastWeek: WeekRange
}

interface WeekDataProps {
  lastWeekData: WeekData
  thisWeekData: WeekData
  weekRangeData: WeekRangeProps
}

export default function ReportFieldWeekLineChart({
  lastWeekData,
  thisWeekData,
  weekRangeData,
}: WeekDataProps) {
  const lastWeek: number[] = [
    lastWeekData.mon.writing +
      lastWeekData.mon.speaking +
      lastWeekData.mon.lectures,
    lastWeekData.tue.writing +
      lastWeekData.tue.speaking +
      lastWeekData.tue.lectures,
    lastWeekData.wed.writing +
      lastWeekData.wed.speaking +
      lastWeekData.wed.lectures,
    lastWeekData.thu.writing +
      lastWeekData.thu.speaking +
      lastWeekData.thu.lectures,
    lastWeekData.fri.writing +
      lastWeekData.fri.speaking +
      lastWeekData.fri.lectures,
    lastWeekData.sat.writing +
      lastWeekData.sat.speaking +
      lastWeekData.sat.lectures,
    lastWeekData.sun.writing +
      lastWeekData.sun.speaking +
      lastWeekData.sun.lectures,
  ]

  const thisWeek: number[] = [
    thisWeekData.mon.writing +
      thisWeekData.mon.speaking +
      thisWeekData.mon.lectures,
    thisWeekData.tue.writing +
      thisWeekData.tue.speaking +
      thisWeekData.tue.lectures,
    thisWeekData.wed.writing +
      thisWeekData.wed.speaking +
      thisWeekData.wed.lectures,
    thisWeekData.thu.writing +
      thisWeekData.thu.speaking +
      thisWeekData.thu.lectures,
    thisWeekData.fri.writing +
      thisWeekData.fri.speaking +
      thisWeekData.fri.lectures,
    thisWeekData.sat.writing +
      thisWeekData.sat.speaking +
      thisWeekData.sat.lectures,
    thisWeekData.sun.writing +
      thisWeekData.sun.speaking +
      thisWeekData.sun.lectures,
  ]

  const options: ChartOptions<'line'> = {
    maintainAspectRatio: false,
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        enabled: false,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  }

  const labels: string[] = ['월', '화', '수', '목', '금', '토', '일'] //x축 기준

  const data: ChartData<'line', number[]> = {
    labels,
    datasets: [
      {
        label: '지난 주',
        data: lastWeek,
        borderColor: '#F6C4E8',
        cubicInterpolationMode: 'monotone',
        pointBackgroundColor: '#F162CA',
        pointBorderColor: '#F162CA',
        pointRadius: 5,
      },
      {
        label: '이번 주',
        data: thisWeek,
        borderColor: '#DAD8FF',
        cubicInterpolationMode: 'monotone',
        pointBackgroundColor: '#4F46F2',
        pointBorderColor: '#4F46F2',
        pointRadius: 5,
      },
    ],
  }
  return (
    <div className="md:p-3 xl:px-11 xl:py-6 bg-white rounded-2xl w-full h-full">
      <div className="flex mb-8">
        <div className="inline-flex items-center mr-8">
          <div className="xl:w-[3rem] md:w-[1rem] h-[1rem] mr-1.5 bg-pink-400 rounded-2xl my-auto" />
          <p className="text-pink-400">{`${weekRangeData.lastWeek.startDate} ~ ${weekRangeData.lastWeek.endDate}`}</p>
        </div>
        <div className="inline-flex items-center">
          <div className="xl:w-[3rem] md:w-[1rem] h-[1rem] mr-1.5 bg-primary-400 rounded-2xl my-auto" />
          <p className="text-primary-400">{`${weekRangeData.thisWeek.startDate} ~ ${weekRangeData.thisWeek.endDate}`}</p>
        </div>
      </div>
      <div className="w-full aspect-[5/2]">
        <Line options={options} data={data} />
      </div>
    </div>
  )
}
