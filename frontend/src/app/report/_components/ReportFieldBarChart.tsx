'use client'

import {
  Chart as ChartJS,
  ChartData,
  ChartOptions,
  CategoryScale,
  LinearScale,
  BarController,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js'
import { Bar } from 'react-chartjs-2'
import ChartDataLabels from 'chartjs-plugin-datalabels'
import { Context } from 'chartjs-plugin-datalabels'
import { WeekData } from '@/app/report/_components/ReportFieldWeekLineChart'
import { WeekRange } from '@/app/report/_components/ReportFieldLayout'

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarController,
  BarElement,
  Title,
  Tooltip,
  Legend
)

interface ThisWeekDataProps {
  thisWeekData: WeekData
  thisWeekRange: WeekRange
}

export default function ReportFieldBarChart({
  thisWeekData,
  thisWeekRange,
}: ThisWeekDataProps) {
  const dictationData: number[] = [
    thisWeekData.mon.writing,
    thisWeekData.tue.writing,
    thisWeekData.wed.writing,
    thisWeekData.thu.writing,
    thisWeekData.fri.writing,
    thisWeekData.sat.writing,
    thisWeekData.sun.writing,
  ]

  const speakingData: number[] = [
    thisWeekData.mon.speaking,
    thisWeekData.tue.speaking,
    thisWeekData.wed.speaking,
    thisWeekData.thu.speaking,
    thisWeekData.fri.speaking,
    thisWeekData.sat.speaking,
    thisWeekData.sun.speaking,
  ]

  const lectureData: number[] = [
    thisWeekData.mon.lectures,
    thisWeekData.tue.lectures,
    thisWeekData.wed.lectures,
    thisWeekData.thu.lectures,
    thisWeekData.fri.lectures,
    thisWeekData.sat.lectures,
    thisWeekData.sun.lectures,
  ]

  const options: ChartOptions<'bar'> = {
    maintainAspectRatio: false,
    responsive: true,
    plugins: {
      legend: {
        display: false,
      },
      tooltip: {
        enabled: false,
      },
      datalabels: {
        listeners: {
          enter: function (context: Context) {
            ;(context as any).hovered = true

            return true
          },
          leave: function (context: Context) {
            ;(context as any).hovered = false
            return true
          },
        },
        color: function (context: Context) {
          return (context as any).hovered
            ? 'white'
            : (context as any).dataset.backgroundColor
        },
        display: function (context: Context) {
          return (context as any).dataset.data[context.dataIndex] > 1
        },
      },
    },
    scales: {
      x: {
        stacked: true,
      },
      y: {
        beginAtZero: true,
        stacked: true,
      },
    },
  }

  const data: ChartData<'bar', number[]> = {
    labels: ['월', '화', '수', '목', '금', '토', '일'],
    datasets: [
      {
        label: '받아쓰기',
        data: dictationData,
        backgroundColor: '#4F46F2',
      },
      {
        label: 'AI회화',
        data: speakingData,
        backgroundColor: '#7A72FF',
      },
      {
        label: '강의',
        data: lectureData,
        backgroundColor: '#C7C4FF',
      },
    ],
  }
  return (
    <div className="md:p-3 xl:px-11 xl:py-6 bg-white rounded-2xl w-full h-full">
      <div className="flex justify-between mb-8">
        <p className="text-primary-400 mr-2 flex items-center xl:ml-8 whitespace-nowrap md:text-sm lg:text-base">
          {`${thisWeekRange.startDate} ~ ${thisWeekRange.endDate}`}
        </p>
        <div className="flex md:gap-1 lg:gap-4 xl:gap-8 lg:flex-grow justify-end">
          <div className="inline-flex items-center">
            <div className="xl:w-[3rem] w-[1rem] h-[1rem] mr-1.5 bg-primary-400 rounded-2xl my-auto" />
            <p className="text-primary-400 whitespace-nowrap md:text-xs lg:text-base">
              받아쓰기
            </p>
          </div>
          <div className="inline-flex items-center">
            <div className="xl:w-[3rem] w-[1rem] h-[1rem] mr-1.5 bg-primary-300 rounded-2xl my-auto" />
            <p className="text-primary-300 whitespace-nowrap md:text-xs lg:text-base">
              AI회화
            </p>
          </div>
          <div className="inline-flex items-center">
            <div className="xl:w-[3rem] w-[1rem] h-[1rem] mr-1.5 bg-primary-100 rounded-2xl my-auto" />
            <p className="text-primary-100 whitespace-nowrap md:text-xs lg:text-base">
              강의
            </p>
          </div>
        </div>
      </div>
      <div className="w-full aspect-[5/2]">
        <Bar options={options} data={data} plugins={[ChartDataLabels]} />
      </div>
    </div>
  )
}
